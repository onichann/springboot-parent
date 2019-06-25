package com.wt.springboot.web.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author Administrator
 * @date 2019-06-25 下午 5:10
 * PROJECT_NAME springboot-parent
 */
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        //有问题的.....
        ConfigurableApplicationContext applicationContext = applicationStartedEvent.getApplicationContext();
        ServletContext servletContext = ((WebApplicationContext) applicationContext).getServletContext();
    }
}
