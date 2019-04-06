package com.wt.springboot.excel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ExportExcelService {

    private static final Log logger= LogFactory.getLog(ExportExcelService.class);

    private static int widthMultiple=200*20; //格子宽度*倍数
    private static int rowHeight=500; //行高
    private static int titleFontSize=16;//标题字体大小
    private static int headerFontSize=10;//列名字体大小
    private static int bodyFontSize=10;//内容字体大小

    private String datePattern="yyyy-MM-dd"; //日期格式转换成String
    private boolean dateToString=false;  //是否将日期类型的数据转换成String类型

    private HSSFWorkbook workbook; //workbook

    private ITranslateCode translateCode= ITranslateCode::simpleTranslate; //默认表头翻译

    private String sheetName;  //Excel 第一页名称

    private String fileName;  //导出文件名称

    public ExportExcelService(String sheetName, String fileName) {
        this.sheetName = sheetName;
        this.fileName = fileName;
    }
    private ExportExcelService(){

    }

    /**
     * 导出
     * @param headers 表头
     * @param datas 数据
     * @param response
     * @throws Exception
     */
    public void doExport(String [] headers, List<Map> datas, HttpServletResponse response) throws Exception {
        response.reset();
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ URLEncoder.encode(fileName,"UTF-8")+".xls");
        exportExcel(headers,datas,response.getOutputStream());
    }

    private  void exportExcel(String [] headers, List<Map> datas,OutputStream outputStream) throws Exception {
        workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName==null?"Sheet1":sheetName);
        //设定每一列的宽度
        for(int i=0;i<headers.length;i++){
            sheet.setColumnWidth(i,widthMultiple);
        }
        //设置标题
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,headers.length-1));//合并标题的单元格
        HSSFRow rowTitle = sheet.createRow(0);
        rowTitle.setHeight((short) rowHeight);
        HSSFCell cellTitle = rowTitle.createCell(0);
        cellTitle.setCellStyle(buildCellStyle(workbook, (short) titleFontSize,true,false));
        cellTitle.setCellValue("标题");

        //设置列名称
        HSSFRow rowHeader = sheet.createRow(1);
        rowHeader.setHeight((short) rowHeight);
        for(int i=0;i<headers.length;i++){
            HSSFCell cellHeader = rowHeader.createCell(i);
            cellHeader.setCellStyle(buildCellStyle(workbook, (short) headerFontSize,true,true));
            cellHeader.setCellValue(translateCode.translate(headers[i]));
        }

        //设置具体值
        AtomicInteger rownum= new AtomicInteger(2);
        if(!CollectionUtils.isEmpty(datas)){
            datas.forEach(dataMap->{
                HSSFRow rowBody = sheet.createRow(rownum.getAndIncrement());
                rowBody.setHeight((short) rowHeight);
                AtomicInteger columnum= new AtomicInteger(0);
                Arrays.stream(headers).forEach(header->{
                    HSSFCell bodyRowCell = rowBody.createCell(columnum.getAndIncrement());
                    HSSFCellStyle basicCellStyle = buildCellStyle(workbook, (short) bodyFontSize, false, true);
                    buildCell(bodyRowCell,basicCellStyle,dataMap.get(header));
                });
            });
        }

        //设定自动宽度
        for(int i=0;i<headers.length;i++){
            sheet.autoSizeColumn(i);
        }

        //导出
        write(outputStream);
    }

    private void write(OutputStream outputStream) throws IOException {
        workbook.write(outputStream);
        outputStream.flush();
        IOUtils.closeQuietly(outputStream);
    }

    /**
     * 设置格子值
     * @param cell
     * @param basicCellStyle
     * @param value
     */
    private void buildCell(HSSFCell cell, CellStyle basicCellStyle, Object value){
        cell.setCellStyle(basicCellStyle);
        if(value==null){
            cell.setCellValue("");
        }else if(value instanceof Integer){
            cell.setCellValue((Integer)value);
            cell.setCellStyle(buildIntegerCellStyle(workbook,basicCellStyle));
        }else if(value instanceof Date){
            if(!dateToString){
                cell.setCellValue((Date) value);
                cell.setCellStyle(buildDateCellStyle(workbook,basicCellStyle));
            }else{
                cell.setCellValue(new SimpleDateFormat(datePattern).format((Date)value));
            }
        }else if(value instanceof LocalDate){
            if(!dateToString){
                ZoneId zone = ZoneId.systemDefault();
                Instant instant = ((LocalDate) value).atStartOfDay().atZone(zone).toInstant();
                Date date = Date.from(instant);
                cell.setCellValue(date);
                cell.setCellStyle(buildDateCellStyle(workbook,basicCellStyle));
            }else{
                cell.setCellValue(((LocalDate) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        } else if (value instanceof LocalDateTime) {
            if (!dateToString) {
                ZoneId zone = ZoneId.systemDefault();
                Instant instant = ((LocalDateTime) value).atZone(zone).toInstant();
                Date date = Date.from(instant);
                cell.setCellValue(date);
                cell.setCellStyle(buildDateTimeCellStyle(workbook, basicCellStyle));
            } else {
                cell.setCellValue(((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        } else {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(String.valueOf(value));
        }
    }

    /**
     * 日期类型
     * @param workbook
     * @param basicCellStyle
     * @return
     */
    private CellStyle buildDateCellStyle(Workbook workbook,CellStyle basicCellStyle){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(basicCellStyle);
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
        return cellStyle;
    }

    /**
     * 日期时间类型
     * @param workbook
     * @param basicCellStyle
     * @return
     */
    private CellStyle buildDateTimeCellStyle(Workbook workbook,CellStyle basicCellStyle){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(basicCellStyle);
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd HH:mm:ss"));
        return cellStyle;
    }

    /**
     * 整数类型
     * @param workbook
     * @param basicCellStyle
     * @return
     */
    private CellStyle buildIntegerCellStyle(Workbook workbook,CellStyle basicCellStyle){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(basicCellStyle);
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
        return cellStyle;
    }

    /**
     *
     * @param workbook
     * @param fontSize
     * @param isBold 是否粗体
     * @param iswrapped//是否自动换行
     * @return
     */
    private static HSSFCellStyle buildCellStyle(HSSFWorkbook workbook, short fontSize, boolean isBold, boolean iswrapped){
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderBottom(BorderStyle.THIN);//下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints(fontSize);// 设置字体大小
        font.setBold(isBold); //字体是否粗体
        cellStyle.setFont(font);
        cellStyle.setWrapText(iswrapped);//自动换行
        return cellStyle;

    }




    public void setTranslateCode(ITranslateCode translateCode) {
        this.translateCode = translateCode;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
