package com.wt.springboot.web.listener;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Administrator
 * @date 2019-04-02 上午 9:08
 * PROJECT_NAME sand-demo
 */
public class ApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
        ConfigurableApplicationContext applicationContext = applicationPreparedEvent.getApplicationContext();
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
//        AbstractBeanDefinition rootBeanDefinition = new RootBeanDefinition(SandContext.class);
//        beanFactory.registerBeanDefinition("SandContext",rootBeanDefinition);
//        beanFactory.registerSingleton("sandContext",SandContext.getInstance());
    }
}
