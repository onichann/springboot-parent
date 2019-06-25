package com.wt.springboot.web.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Administrator
 * @date 2019-04-01 上午 11:41
 * PROJECT_NAME sand-demo
 */
@WebListener
public class WebContextListener implements ServletContextListener {

    private static final Log logger = LogFactory.getLog(WebContextListener.class);


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        logger.info("web listener 启动");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
