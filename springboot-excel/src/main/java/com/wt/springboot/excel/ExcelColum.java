package com.wt.springboot.excel;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
@Documented
public @interface ExcelColum {


    String columnName() default "";

    String message() default "";
}
