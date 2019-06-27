package com.wt.springboot.web.listener;

/**
 * @author Administrator
 * @date 2019-06-27 下午 4:30
 * PROJECT_NAME springboot-parent
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * 容器关闭
 */
public class ContextClosedListener implements ApplicationListener<ContextClosedEvent>{
    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        ApplicationContext applicationContext = contextClosedEvent.getApplicationContext();
    }
}
