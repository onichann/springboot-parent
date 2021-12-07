package com.wt.springboot.dingding.gc;

/**
 * @author Pat.Wu
 * @date 2021/12/7 16:20
 * @description
 */
public class GCTest {
    public static void main(String[] args) {
        byte[] allocation1,allocation2;
        allocation1 = new byte[90*1024 * 1024];
        //
        System.gc();
    }
}
