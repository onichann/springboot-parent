package com.wt.springboot.aop.aop1;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Administrator
 * @date 2020-01-03 上午 11:04
 * description
 */
public class ProxyBean implements InvocationHandler {

    private Object target = null;
    private Interceptor interceptor = null;

    public Object getProxyBean(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean exceptionFlag=false;
        Invocation invocation = new Invocation(args, method, target);
        System.out.println("======调用方法：" + method.getName() + "======");
        Object retObj = null;
        try {
            try {
                this.interceptor.before();
                if (this.interceptor.userAround()) {
                    retObj = this.interceptor.around(invocation);
                } else {
                    retObj = method.invoke(target, args);
                }
            } finally {
                this.interceptor.after();
            }
            this.interceptor.afterReturning();
        } catch (Exception e) {
            this.interceptor.afterThrowing();
        }
        return retObj;
    }
}
