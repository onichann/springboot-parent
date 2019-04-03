package com.wt.springboot.config;

import com.wt.springboot.SpringbootWebApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter({RestTemplateAutoConfiguration.class, SpringbootWebApplication.class})
public class MyConfig {

    @Bean
    @ConditionalOnMissingBean
//    @DependsOn("restTemplate")
//    @ConditionalOnBean(RestTemplate.class)
//    @Lazy
    public String abc(){
        return "abc";
    }


}
