package com.wt.springboot.excel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImportExcelService {

    private static final Log logger= LogFactory.getLog(ExportExcelService.class);

    private Workbook workBook;

    private int headerRow=1; //表头所在行数，默认第一行为title ,0为第一行

    private int sheetNo=0; //excel 第几个sheet，默认第一页 ,0 为第一页

    private List<CellBean> headerCellBeans=new ArrayList<>();//头部信息

    private List<CellBean> bodyCellBeans=new ArrayList<>();//内容信息

    private List<CellBean> errorCellBeans=new ArrayList<>();//验证失败的cell

    private List successList = new ArrayList();  //读取成功的行

    private List<Map> errorList = new ArrayList(); //读取失败的行 存放读取成JavaBean的失败的行

    private ValidHandler validHandler; //验证器

    private DictTranslateCode dictTranslateCode=new DictTranslateCode(); //字典


    {
        ValidHandler notNullHandler= new NotNullOrEmptyValidHandler();
        ValidHandler stringLengthValidHandler = new StringLengthValidHandler();
        ValidHandler simpleValidHandler= new SimpleValidHandler();
        notNullHandler.setNextValidHandler(stringLengthValidHandler);
        stringLengthValidHandler.setNextValidHandler(simpleValidHandler);
        validHandler=notNullHandler;
    }

    /**
     *默认导入返回List<Map>
     * @param inputStream 文件流
     * @return
     */
    public List<Map> importExcel(InputStream inputStream) {
        return this.importExcel(inputStream,Map.class);
    }

    /**
     * 导入excel
     * @param inputStream  文件流
     * @param tClass
     * @param <T>  返回的类型 Map 或JavaBean
     * @return
     */
    public <T> List<T> importExcel(InputStream inputStream,Class<T> tClass) {
        List<T> list=new ArrayList<>();
        try {
            workBook= WorkbookFactory.create(inputStream);
        } catch (IOException | InvalidFormatException e) {
            logger.error("读取Excel失败!",new RuntimeException("load Excel Error!"));
        }
        Sheet sheet = workBook.getSheetAt(sheetNo);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            if(row.getRowNum()<headerRow){
                continue;
            }
            //读取头部
            if(row.getRowNum()==headerRow){
                //header为空的数据不读取
                ArrayList<CellBean> rowInfo = getRowInfo(row);
                headerCellBeans.addAll(rowInfo);
            }else{
                //读取内容
                ArrayList<CellBean> rowInfo = getRowInfo(row);
                bodyCellBeans.addAll(rowInfo);
                if(Map.class.isAssignableFrom(tClass)){
                    Map<String,Object> map= convertToMap(headerCellBeans,rowInfo);
                    list.add((T) map);
                }else{
                    ExcelBean excelBean = tClass.getDeclaredAnnotation(ExcelBean.class);
                    if(excelBean!=null){
                        T bean=convertToBean(headerCellBeans,rowInfo,tClass);
                        if(bean!=null){
                            list.add(bean);
                        }else {
                            errorList.add(convertToMap(headerCellBeans,rowInfo));
                        }
                    }
                }
            }
        }
        successList=list;
        //如果有验证失败的数据
        if(!errorCellBeans.isEmpty()){
            return new ArrayList<>();
        }
        return list;
    }

    /**
     * 读取数据转换成bean
     * @param headerCellBeans
     * @param rowInfo
     * @param tClass
     * @param <T>
     * @return
     */
    private <T> T convertToBean(List<CellBean> headerCellBeans, ArrayList<CellBean> rowInfo, Class<T> tClass) {
        T bean=null;
        AtomicBoolean thisRowHasError= new AtomicBoolean(false);
        try {
            Constructor<T> tConstructor = tClass.getDeclaredConstructor();
            tConstructor.setAccessible(true);
            bean=tConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            logger.debug(e.getMessage());
        }

        //过滤出需要导入的列 被@ExcelColum标注的
        ArrayList<CellBean> beanCloumns=trimExportColumn(headerCellBeans, tClass);

        T finalBean = bean;
        beanCloumns.stream().sorted(Comparator.comparingInt(CellBean::getCellColumn)).forEach(header->{
            Field field = Objects.requireNonNull(getField(header.getFieldName(), tClass));
            field.setAccessible(true);
            CellBean valueCell =rowInfo.stream().filter(row->row.getCellColumn()==header.getCellColumn()).findFirst().orElse(CellBean.empty());
            //防止body数据为空没有去读格子返回空bean没列和行
            valueCell.setCellColumn(header.getCellColumn());
            valueCell.setFieldName(header.getFieldName());
            Object value=valueCell.getCellValue();
            //翻译
            value=translateValue(field.getName(), value);
            valueCell.setCellValue(value);
            //验证当前行的数据是否符合规范
            ValidResponse response=doValidation(field,valueCell);
            if(response.isSuccess()){
                if(!thisRowHasError.get()){
                    try {
                        ReflectionUtils.setField(field, Objects.requireNonNull(finalBean),parseValue(field,value));
                    } catch (Exception e) {
                        logger.debug(e.getMessage());
                    }
                }
            }else{
                thisRowHasError.set(true);
                errorCellBeans.add(valueCell.setError(true).setErrorMessage(response.getErrorMessage()));
            }
        });
        if(!thisRowHasError.get()){
            return finalBean;
        }
        return null;
    }

    /**
     * 字典项为列名大写 将字典翻译的值翻译成字典code
     * @param fieldName
     * @param value
     * @return
     */
    private Object translateValue(String fieldName, Object value){
        if(Strings.isBlank(fieldName)||ObjectUtils.isEmpty(value)){
            return value;
        }
        //默认字符串类型才能翻译
        if(value instanceof String){
            return dictTranslateCode.translateCodeValue2CodeKey(fieldName,Objects.toString(value));
        }
        return value;
    }

    private Object parseValue(Field field,Object value){
        String typeName=field.getType().getName();
        Object o=null;
        if(value==null){
            return null;
        }
        switch (typeName){
            case "int":
                o=NumberUtils.parseNumber(Objects.toString(value), Integer.class); break;
            case "java.lang.Double":
                o=NumberUtils.parseNumber(Objects.toString(value), Double.class); break;
            case "boolean":
                o=Boolean.parseBoolean(Objects.toString(value));break;
            case "float":
                o=NumberUtils.parseNumber(Objects.toString(value), Float.class);break;
            case "java.util.Date":
                o= value;
                break;
            case "java.time.LocalDate":
                o=DateUtils.date2LocalDate((Date) value);
                break;
            case "java.time.LocalDateTime":
                o = DateUtils.date2LocalDateTime((Date) value);
                break;
            case "java.lang.String":
                o=value;
                break;
                default:
                    o=value;
        }
        return o;
    }


    private ValidResponse doValidation(Field field,CellBean bean){
        ValidRequest request=new ValidRequest().setField(field).setCellBean(bean);
        return validHandler.doValid(request);
    }


    private <T> Field getField(String fieldName, Class<T> tClass){
        try {
            return tClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * 过滤出需要导入的列 被@ExcelColum标注的且cloumnName与表头匹配的
     * @param headerCellBeans
     * @param tClass
     */
    private <T> ArrayList<CellBean> trimExportColumn(List<CellBean> headerCellBeans, Class<T> tClass) {
        ArrayList<CellBean> beanVector=new ArrayList<>();
        Field[] declaredFields = tClass.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field->{
            ExcelColum excelColum = field.getDeclaredAnnotation(ExcelColum.class);
            if(excelColum!=null){
                String cloumnName = excelColum.columnName();
                if(!StringUtils.isEmpty(cloumnName)){
                    headerCellBeans.stream().filter(header -> cloumnName.equals(header.getCellValue())).findFirst().ifPresent(cellbean->{
                        cellbean.setFieldName(field.getName());
                        beanVector.add(cellbean);
                    });
                }
            }
        });
        return beanVector;
    }

    /**
     * 获取cell的值
     * @param cell
     * @return
     */
    private Object getCellOriginValue(Cell cell){
        Object o=null;
        switch (cell.getCellTypeEnum()){
            case STRING:
                o=cell.getStringCellValue();
                break;
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)){
                    o=cell.getDateCellValue();
                }else{
                    DecimalFormat df = new DecimalFormat("#.#########");
                    o=df.format(Double.valueOf(cell.getNumericCellValue()));
                }
                break;
            case BOOLEAN:
                o = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                o = cell.getCellFormula(); //目前指获取公式代码，未解析公式数值  https://blog.csdn.net/maotai_2010/article/details/51673791
                break;
                //其他类型默认不读取
                default:
                    o = "";
        }
        return o;
    }

    /**
     * 读取当前row的所有格子信息
     * @param row
     * @return
     */
    private ArrayList<CellBean> getRowInfo(Row row){
        ArrayList<CellBean> cellBeans=new ArrayList<>();
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()){
            Cell cell = cellIterator.next();
            cellBeans.add(new CellBean().setCellRow(cell.getRowIndex())
                    .setCellColumn(cell.getColumnIndex()).setCellValue(getCellOriginValue(cell))
                    .setCellType(cell.getCellTypeEnum()).setError(false));
        }
        return cellBeans;
    }

    /**
     * 将读取的数据转换成List<Map>
     * @param headerCellBeans
     * @param rowBeans
     * @return
     */
    private Map<String,Object> convertToMap(List<CellBean> headerCellBeans,List<CellBean> rowBeans){
        Map<String,Object> map=new LinkedHashMap<>();
        headerCellBeans.stream().sorted(Comparator.comparingInt(CellBean::getCellColumn)).forEachOrdered(header->map.put(ObjectUtils.nullSafeToString(header.getCellValue()),rowBeans.stream().filter(row->row.getCellColumn()==header.getCellColumn()).findFirst().orElse(CellBean.empty()).getCellValue()));
        return map;
    }

    /**
     *可以自定义验证处理器,默认优先处理用户自定义的验证
     * @see NotNullOrEmptyValidHandler
     * @param validHandler
     */
    public void setValidHandler(ValidHandler validHandler) {
        validHandler.setNextValidHandler(validHandler);
    }

    /**
     * 设置字典
     * @param typeName
     * @param typeCode
     * @return
     */
    public DictTranslateCode.DictType setDict(String typeName, String typeCode) {
        return dictTranslateCode.setDict(typeName, typeCode);
    }

    public int getHeaderRow() {
        return headerRow;
    }

    public void setHeaderRow(int headerRow) {
        this.headerRow = headerRow;
    }

    public int getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(int sheetNo) {
        this.sheetNo = sheetNo;
    }

    public List<CellBean> getErrorCellBeans() {
        return errorCellBeans;
    }

    public List getSuccessList() {
        return successList;
    }

    public List<Map> getErrorList() {
        return errorList;
    }

    public ImportExcelService(int headerRow, int sheetNo) {
        this.headerRow = headerRow;
        this.sheetNo = sheetNo;
    }

    public ImportExcelService(int sheetNo) {
        this.sheetNo = sheetNo;
    }

    public ImportExcelService() {
    }
}
