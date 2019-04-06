package com.wt.springboot.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @author Administrator
 * @date 2019-04-06 下午 11:09
 * PROJECT_NAME springboot-parent
 */
@Configuration
public class validatorConfiguration {

    //spring实现对方法参数的校验
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
