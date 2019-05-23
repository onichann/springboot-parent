package com.wt.springboot.executor;

/**
 * @author Administrator
 * @date 2019-05-23 上午 10:31
 * PROJECT_NAME springboot-parent
 */
//        https://blog.csdn.net/u011514810/article/details/77131296
public class ThreadNotifyHelper {

    static String[] buildCharArr(){
        String[] charArr=new String[26];
        int a=65;
        for(int i=0;i<26;i++){
            charArr[i] = String.valueOf((char)(i + a));
        }
        return charArr;
    }

    static String[] buildNoArr(){
        String[] noArr = new String[52];
        for(int i=0;i<52;i++){
            noArr[i] = String.valueOf(i + 1);
        }
        return noArr;
    }
}
