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
           for(int i=0;i<nos.length;i+=2){
                    synchronized (datas){
                        while(datas.tag){
                            try {
                                datas.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                break;
                            }
                        }
                        System.out.print(nos[i]+nos[i+1]);
                        datas.tag=true;
                        datas.notifyAll();
                    }
                }
        }
    }

    class B implements Runnable{
        String[] chars = ThreadHelper.buildCharArr();
        @Override
        public void run() {
            for(int i=0;i<chars.length;i++){
                synchronized (datas){
                    while(!datas.tag){
                        try {
                            datas.wait();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break ;
                        }
                    }
                    System.out.print(chars[i]);
                    datas.tag=false;
                    datas.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(Arrays.toString(ThreadNotify.ThreadHelper.buildCharArr()));
        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        executorService.submit(new ThreadNotify().new A());
//        executorService.submit(new ThreadNotify().new B());
//        executorService.shutdown();
//        https://blog.csdn.net/u011514810/article/details/77131296

        executorService.submit(new ThreadNotify().newThreadOne());
        executorService.submit(new ThreadNotify().newThreadTwo());
        executorService.shutdown();

    }


    private final ThreadToGo threadToGo = new ThreadToGo();
    public Runnable newThreadOne() {
        final String[] inputArr = ThreadHelper.buildNoArr();
        return new Runnable() {
            private String[] arr = inputArr;
            public void run() {
                try {
                    for (int i = 0; i < arr.length; i=i+2) {
                        synchronized (threadToGo) {
                            while (threadToGo.value == 2)
                                threadToGo.wait();
                            System.out.print(arr[i]+arr[i + 1]);
                            threadToGo.value = 2;
                            threadToGo.notify();
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Oops...");
                }
            }
        };
    }
    public Runnable newThreadTwo() {
        final String[] inputArr = ThreadHelper.buildCharArr();
        return new Runnable() {
            private String[] arr = inputArr;
            public void run() {
                try {
                    for (int i = 0; i < arr.length; i++) {
                        synchronized (threadToGo) {
                            while (threadToGo.value == 1)
                                threadToGo.wait();
                            System.out.print(arr[i]);
                            threadToGo.value = 1;
                            threadToGo.notify();
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Oops...");
                }
            }
        };
    }
    class ThreadToGo {
        int value = 1;
    }
}
