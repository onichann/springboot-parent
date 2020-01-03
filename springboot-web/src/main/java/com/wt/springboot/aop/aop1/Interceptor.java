package com.wt.springboot.aop.aop1;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Administrator
 * @date 2020-01-03 上午 10:42
 * description
 */
public interface Interceptor {
    public boolean before();

    public void after();

    public Object around(Invocation invocation) throws InvocationTargetException, IllegalAccessException;

    public void afterReturning();

    public void afterThrowing();

    boolean userAround();
}
