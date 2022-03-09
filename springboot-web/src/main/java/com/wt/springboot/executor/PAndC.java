package com.wt.springboot.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pat.Wu
 * @date 2022/2/25 15:45
 * @description
 */
public class PAndC {

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition=lock.newCondition();

    static class Data {
        private volatile boolean flag = false;

        private Object object;

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }

    class A implements Runnable{

        private Data data;

        public A(Data data) {
            this.data = data;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (data.flag) {
                        condition.await();
                    }
                    data.setObject(System.currentTimeMillis());
                    System.out.println("生产："+data.getObject());
                    TimeUnit.SECONDS.sleep(1);
                    data.flag=true;
                    condition.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }

        }
    }

    class B implements Runnable{

        private Data data;

        public B(Data data) {
            this.data = data;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (!data.flag) {
                        condition.await();
                    }
                    System.out.println("消费："+data.getObject());
                    TimeUnit.SECONDS.sleep(1);
                    data.flag=false;
                    condition.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }

        }
    }

    public static void main(String[] args) {
        Data data=new Data();
        data.setObject(System.currentTimeMillis());
        data.setFlag(true);
        PAndC pAndC = new PAndC();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.submit(pAndC.new A(data));
        executorService.submit(pAndC.new B(data));

    }


}
