package com.wt.springboot.common;

import org.springframework.context.ApplicationContext;


public class SpringContextUtil {

    private static ApplicationContext ctx;

    public static ApplicationContext getCtx() {
        return ctx;
    }

    public static void setCtx(ApplicationContext ctx) {
        SpringContextUtil.ctx = ctx;
    }
}
