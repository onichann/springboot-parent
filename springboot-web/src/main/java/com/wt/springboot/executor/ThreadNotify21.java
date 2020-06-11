package com.wt.springboot.executor;


import com.wt.Mutex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author Administrator
 * @date 2019-05-23 上午 10:35
 * PROJECT_NAME springboot-parent
 */
public class ThreadNotify21 {
    private Lock lock =new Mutex();
    private Condition condition=lock.newCondition();

    private static class Datas{
        private boolean tag=false;
    }

    private final static Datas datas=new Datas();

    class A implements Runnable{
        final String[] nos = ThreadNotifyHelper.buildNoArr();
        @Override
        public void run() {
            for(int i=0;i<nos.length;i+=2){
                try {
                    lock.lock();
                    while(datas.tag){
                        condition.await();
                    }
                    System.out.print(nos[i]+nos[i+1]);
                    datas.tag=true;
                    condition.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
    class B implements Runnable{
        String[] chars = ThreadNotifyHelper.buildCharArr();
        @Override
        public void run() {
            for(int i=0;i<chars.length;i++){
                try {
                    lock.lock();
                    while (!datas.tag){
                        condition.await();
                    }
                    System.out.print(chars[i]);
                    datas.tag=false;
                    condition.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ThreadNotify21 threadNotify2 = new ThreadNotify21();
        executorService.submit(threadNotify2.new B());
        executorService.submit(threadNotify2.new A());
        executorService.shutdown();
    }

}
