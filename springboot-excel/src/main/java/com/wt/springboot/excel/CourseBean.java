package com.wt.springboot.excel;


import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@ExcelBean
public class CourseBean{

    @NotNullOrEmpty(message = "主键index不能为空")
    @ExcelColum(columnName = "序号",message = "数据类型应该为整型")
    private int index;

    @ExcelColum(columnName = "课程名称")
    @Length(max = 5,message = "课程名称长度不能超过5")
    private String kcmc;

    @ExcelColum(columnName ="课程日期" )
    @NotNullOrEmpty
    private LocalDate kcrq;

    @ExcelColum(columnName = "课程类型")
    private int kclx;

    @ExcelColum(columnName = "授课老师")
    @NotNullOrEmpty(message = "授课老师不能为空")
    private String skls;

    @ExcelColum(columnName = "所在班级")
    private String szbj;

    @ExcelColum(columnName = "课程时间")
    private LocalDateTime kcsj;

    @ExcelColum(columnName = "课程时间2")
    private Date kcsj2;

//    @ExcelColum(columnName = "课程时间3")
    private LocalDateTime kcsj3;

    @ExcelColum(columnName = "分数")
    private Double course;

    @ExcelColum(columnName = "是否是皮卡丘")
    private boolean isMan;

    private CourseBean(){

    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "index=" + index +
                ", kcmc='" + kcmc + '\'' +
                ", kcrq=" + kcrq +
                ", kclx=" + kclx +
                ", skls='" + skls + '\'' +
                ", szbj='" + szbj + '\'' +
                ", kcsj=" + kcsj +
                ", kcsj2=" + kcsj2 +
                ", kcsj3=" + kcsj3 +
                ", course=" + course +
                ", isMan=" + isMan +
                '}';
    }
}
