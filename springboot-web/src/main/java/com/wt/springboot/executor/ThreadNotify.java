package com.wt.springboot.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 * @date 2019-05-22 下午 5:06
 * PROJECT_NAME springboot-parent
 */
//编写两个线程，一个线程打印1~52，另一个线程打印字母A~Z，打印顺序为12A34B56C……5152Z，要求使用线程间的通信。
public class ThreadNotify {

    static class ThreadHelper{
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

    private class Datas{
        private  boolean tag=false;
    }

    private final Datas datas=new Datas();

    class A implements Runnable{
        final String[] nos = ThreadHelper.buildNoArr();
        @Override
        public void run() {
           a: for(int i=0;i<nos.length;i+=2){
                    synchronized (datas){
                        while(datas.tag){
                            try {
                                datas.wait();

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                break a;
                            }
                        }
                        System.out.print(nos[i]+nos[i+1]);
                        datas.tag=true;
                        datas.notify();
                    }
                }
        }
    }

    class B implements Runnable{
        String[] chars = ThreadHelper.buildCharArr();
        @Override
        public void run() {

            b: for(int i=0;i<chars.length;i++){
                synchronized (datas){
                    while(!datas.tag){
                        try {
                            datas.wait();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break b;
                        }
                    }
                    System.out.print(chars[i]);
                    datas.tag=false;
                    datas.notify();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(Arrays.toString(ThreadNotify.ThreadHelper.buildCharArr()));

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new ThreadNotify().new B());
        executorService.submit(new ThreadNotify().new A());
        executorService.shutdown();
//        https://blog.csdn.net/u011514810/article/details/77131296
    }

}
