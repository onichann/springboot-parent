package com.wt.springboot.excel;


import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/excel")
public class TestExcelService {

    /**
     * 导出
     * @param response
     * @throws Exception
     */
    @RequestMapping("/export")
    @ResponseBody
    public void download(HttpServletResponse response) throws Exception {
        ExportExcelService excelService = new ExportExcelService("第一页","某某文件");
        //你可以对表头进行翻译，也可以不翻译
        excelService.setTranslateCode(key -> {
            if("index".equals(key)){
                return "序号";
            }else if("kcmc".equals(key)){
                return "课程名称";
            }else if("kclx".equals(key)){
                return "课程类型";
            }else if("kcrq".equals(key)){
                return "课程日期";
            }else if("skls".equals(key)){
                return "授课老师";
            } else if ("szbj".equals(key)) {
                return "所在班级";
            } else if ("kcsj".equals(key)) {
                return "课程时间";
            }else if ("kcsj2".equals(key)) {
                return "课程时间2";
            }else if ("kcsj3".equals(key)) {
                return "课程时间3";
            }else if ("course".equals(key)) {
                return "分数";
            }else if ("isMan".equals(key)) {
                return "是否是皮卡丘";
            } else {
                return ObjectUtils.nullSafeToString(key);
            }
        });
        excelService.doExport(new String[]{"index","kcmc","kcrq","kclx","skls","szbj","kcsj","kcsj2","kcsj3","course","isMan"},getMapList(),response);

    }

    //main 导入
    public static void main(String[] args) throws FileNotFoundException {
        //默认header在第一行,读取第一个sheet
        ImportExcelService mapImportExcelService = new ImportExcelService();
        //你可以指定header在第几行,以及读取第几页的数据 第一行为0  第一页为0
//        ImportExcelService importExcelService = new ImportExcelService(1,0);
//        ImportExcelService importExcelService = new ImportExcelService(0);
        InputStream resourceAsStream = TestExcelService.class.getClassLoader().getResourceAsStream("test.xls");
        //你可以自己新增校验规则 默认优先处理用户自定义的校验规则 可参考 NotNullOrEmptyValidHandler
//        mapImportExcelService.setValidHandler();
        //你可以设置字典，进行翻译后校验转bean
        mapImportExcelService.setDict("课程类型", "kclx").addDict("1", "语文")
                .addDict("2", "英语").addDict("3","数学");
        //导入读取成List<Bean>
        List<CourseBean> list = mapImportExcelService.importExcel(resourceAsStream,CourseBean.class);
        //如果全部获取成功，你可以直接获取到数据
        list.forEach(System.out::println);
        //你可以获取校验成功的数据有哪些
        System.out.println("-----------获取检测成功的数据-----------");
        List successList = mapImportExcelService.getSuccessList();
        successList.forEach(System.out::println);
        //你可以获取校验失败的数据有哪些
        System.out.println("-----------获取检测失败的数据-----------");
        List<Map> errorList = mapImportExcelService.getErrorList();
        errorList.forEach(System.out::println);
        //你也可以获取具体的哪些格子有问题
        System.out.println("-----------获取检测失败的格子-----------");
        List<CellBean> errorCellBeans = mapImportExcelService.getErrorCellBeans();
        errorCellBeans.forEach(System.out::println);
    }


    //导入解析成List<Map> 无校验
    @Test
    public void importToMap() throws FileNotFoundException {
        ImportExcelService mapImportExcelService = new ImportExcelService();
        File file=new File("test.xls");
        List<Map> list = mapImportExcelService.importExcel(new FileInputStream(file),Map.class);  //导入读取List<Map>
        list.forEach(System.out::println);
    }

    private static Map buildMap(int index, String kcmc, LocalDate kcrq, String kclx,
                                String skls, String szbj, LocalDateTime kcsj, Date kcsj2,LocalDateTime kcsj3,Double course,boolean isMan){
        Map map=new HashMap();
        map.put("index", index);
        map.put("kcmc", kcmc);
        map.put("kcrq", kcrq);
        map.put("kclx", kclx);
        map.put("skls",skls);
        map.put("szbj", szbj);
        map.put("kcsj", kcsj);
        map.put("kcsj2", kcsj2);
        map.put("kcsj3", kcsj3);
        map.put("course", course);
        map.put("isMan", isMan);
        return map;
    }

    private static List<Map> getMapList(){
        List<Map> list=new ArrayList<Map>();
        list.add(buildMap(1, "语文",LocalDate.now(),"语文","老师1","1",LocalDateTime.now(),new Date(),LocalDateTime.now(),12.25,false));
        list.add(buildMap(2, "数学",LocalDate.of(2018,12,12),"英语","老师2","2",LocalDateTime.now(),new Date(),LocalDateTime.now(),128.25,true));
        list.add(buildMap(3, "英语",LocalDate.now(),"数学","老师3","3",LocalDateTime.now(),new Date(),LocalDateTime.now(),12.25,false));
        return list;
    }
}
