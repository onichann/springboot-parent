package com.wt.springboot.core.type;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author wutong
 * @date 2022/5/6 23:07
 * 说明:
 */
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        ConfigurableApplicationContext applicationContext = applicationReadyEvent.getApplicationContext();

    }
}
