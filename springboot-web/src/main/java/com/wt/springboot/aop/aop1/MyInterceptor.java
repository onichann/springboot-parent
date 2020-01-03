package com.wt.springboot.aop.aop1;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Administrator
 * @date 2020-01-03 上午 10:54
 * description
 */
public class MyInterceptor implements Interceptor{

    @Override
    public boolean before() {
        System.out.println("before...");
        return true;
    }

    @Override
    public void after() {
        System.out.println("after...");
    }

    @Override
    public Object around(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        System.out.println("around before...");
        Object obj = invocation.proceed();
        System.out.println("around after...");
        return obj;
    }

    @Override
    public void afterReturning() {
        System.out.println("afterReturning...");
    }

    @Override
    public void afterThrowing() {
        System.out.println("afterThrowing...");
    }

    @Override
    public boolean userAround() {
        return true;
    }
}
