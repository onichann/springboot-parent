package com.wt.springboot.aop.aop1;

import org.apache.logging.log4j.util.Strings;

/**
 * @author Administrator
 * @date 2020-01-03 上午 10:26
 * description
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String name) {
        if (Strings.isEmpty(name)) {
            throw new RuntimeException("name is null");
        }
        System.out.println("hello "+name);
    }

    @Override
    public String toString() {
        return "HelloServiceImpl{}";
    }
}
