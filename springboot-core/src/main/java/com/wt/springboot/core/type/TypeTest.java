package com.wt.springboot.core.type;

import org.springframework.context.ApplicationListener;
import org.springframework.core.ResolvableType;

import java.math.BigDecimal;

/**
 * @author wutong
 * @date 2022/5/6 23:05
 * 说明:
 */
public class TypeTest {
    public static void main(String[] args) {
        ApplicationReadyListener applicationReadyListener = new ApplicationReadyListener();
        ResolvableType resolvableType = ResolvableType.forInstance(applicationReadyListener);
//        System.out.println(resolvableType.getInterfaces()[0]);
//        System.out.println(resolvableType.getRawClass());
//        System.out.println(resolvableType.getGenerics().length);
//        System.out.println(resolvableType.getNested(0));
        System.out.println(resolvableType.getInterfaces()[0].getGeneric(0).resolve());
        System.out.println(ResolvableType.forClass(applicationReadyListener.getClass()).as(ApplicationListener.class).getGeneric());
        System.out.println(resolvableType.getInterfaces()[0].resolveGeneric(0));
//        new Typerefer

        int i = new BigDecimal(3).multiply(new BigDecimal("0.5")).intValue();
        System.out.println(i);
        System.out.println(4>>1);
    }
}
