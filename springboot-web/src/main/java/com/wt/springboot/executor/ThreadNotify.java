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

    private static class Datas{
        private boolean tag=false;
    }

    private final static Datas datas=new Datas();

    class A implements Runnable{
        final String[] nos = ThreadNotifyHelper.buildNoArr();
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
                        datas.notify();
                    }
                }
        }
    }

    class B implements Runnable{
        String[] chars = ThreadNotifyHelper.buildCharArr();
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
                    datas.notify();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new ThreadNotify().new A());
        executorService.submit(new ThreadNotify().new B());
        executorService.shutdown();
    }

}
