package com.wt.activiti.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @date 2020-03-17 下午 3:05
 * description
 */
@Configuration
public class ActivitiMarkerConfiguration {
    public ActivitiMarkerConfiguration(){

    }

    @Bean
    public ActivitiMarkerConfiguration.Marker activitiMarkerBean() {
        return new Marker();
    }

    class Marker{
        Marker(){

        }
    }

}
