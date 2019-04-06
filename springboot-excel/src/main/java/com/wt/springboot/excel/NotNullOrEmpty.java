package com.wt.springboot.excel;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
@Documented
public @interface NotNullOrEmpty {
    String value() default "数据不能为空";
    String message() default "数据不能为空";
}
