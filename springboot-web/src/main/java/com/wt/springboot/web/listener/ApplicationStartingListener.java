package com.wt.springboot.web.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Administrator
 * @date 2019-06-25 下午 4:37
 * PROJECT_NAME springboot-parent
 */
public class ApplicationStartingListener implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        SpringApplication source = (SpringApplication)applicationStartingEvent.getSource();
    }
}
