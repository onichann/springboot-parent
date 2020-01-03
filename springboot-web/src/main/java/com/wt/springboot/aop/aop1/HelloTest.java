package com.wt.springboot.aop.aop1;

/**
 * @author Administrator
 * @date 2020-01-03 上午 11:21
 * description
 */
public class HelloTest {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ProxyBean proxyBean = new ProxyBean();
        Object obj = proxyBean.getProxyBean(helloService, new MyInterceptor());
        ((HelloService)obj).sayHello("dady");
    }
}
