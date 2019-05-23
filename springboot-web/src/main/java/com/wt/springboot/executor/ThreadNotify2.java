package com.wt.springboot.executor;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Administrator
 * @date 2019-05-23 上午 10:35
 * PROJECT_NAME springboot-parent
 */
public class ThreadNotify2 {
    private Lock lock =new ReentrantLock();
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
        ThreadNotify2 threadNotify2 = new ThreadNotify2();
        executorService.submit(threadNotify2.new A());
        executorService.submit(threadNotify2.new B());
        executorService.shutdown();
    }

}
