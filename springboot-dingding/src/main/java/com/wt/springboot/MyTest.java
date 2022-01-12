package com.wt.springboot;

import java.util.List;

/**
 * @author Pat.Wu
 * @date 2022/1/5 9:59
 * @description
 */
public class MyTest {

    public void get(String a, Integer b) {

    }

    public void get(List<String> a, Integer b) {

    }


    public static void main(String[] args) {
//        Method[] declaredMethods = MyTest.class.getDeclaredMethods();
//        for (Method declaredMethod : declaredMethods) {
//            Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
//            for (Class<?> parameterType : parameterTypes) {
//                System.out.println(parameterType.getSimpleName());
//            }
//        }
//        String s = StringUtils.arrayToDelimitedString(new Object[]{"1", "2"}, ",");
//        System.out.println(s);
        int i=30;
        System.out.println(Integer.toBinaryString(1<<31));
        System.out.println(Integer.toBinaryString(30));
        System.out.println(i|1<<31);
        System.out.println(Integer.toBinaryString(i|1<<31));
        System.out.println(i>>1);
        System.out.println(Integer.toBinaryString(1));
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(i);
    }
}
