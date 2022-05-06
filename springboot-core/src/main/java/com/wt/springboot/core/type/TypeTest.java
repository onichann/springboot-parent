package com.wt.springboot.core.type;

import org.springframework.core.ResolvableType;

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
//        new Typerefer
    }
}
