package com.wt.springboot.web.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author Administrator
 * @date 2019-06-27 下午 4:27
 * PROJECT_NAME springboot-parent
 */

/**
 * 容器启动后
 */
public class ContextRefreshedListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        ApplicationContext applicationContext = contextClosedEvent.getApplicationContext();
    }
}
