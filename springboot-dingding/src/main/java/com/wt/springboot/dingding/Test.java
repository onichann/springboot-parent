package com.wt.springboot.dingding;

/**
 * @author Pat.Wu
 * @date 2021/12/8 10:12
 * @description
 */
public class Test {

    public static void main(String[] args) {
        new Test().test();
    }
    public static String i = "111";

    public void test() {

        for (int j = 0; j < 2; j++) {
            System.out.println("i="+i);
            i=a1(i);
        }

    }

    public String a1(String a){
        a+=1;
        System.out.println("a="+a);
        return a;
    }
}
