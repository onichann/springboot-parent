package com.wt.activiti.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Administrator
 * @date 2020-03-17 下午 3:04
 * description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({BPSAutoConfigurationImportSelector.class})
public @interface EnableBPSWorkflow {
}
