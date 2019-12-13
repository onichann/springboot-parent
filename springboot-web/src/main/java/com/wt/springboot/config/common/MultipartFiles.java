package com.wt.springboot.config.common;

import java.lang.annotation.*;

/**
 * @author Administrator
 * @date 2019-12-12 下午 6:36
 * PROJECT_NAME gtyzt-sand
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultipartFiles {
}
