package com.wt.springboot.executor;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 * @date 2019-05-23 上午 10:35
 * PROJECT_NAME springboot-parent
 */
public class ThreadNotify3 {
    private List<String> list = Collections.synchronizedList(new ArrayList<>());
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () ->{
        Collections.sort(list);
        list.forEach(System.out::print);
        list.clear();
    });

    class A implements Runnable{
        final String[] nos = ThreadNotifyHelper.buildNoArr();
        @Override
        public void run() {
            for(int i=0;i<nos.length;i+=2){
                list.add(nos[i]);
                list.add(nos[i + 1]);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class B implements Runnable{
        String[] chars = ThreadNotifyHelper.buildCharArr();
        @Override
        public void run() {
            for (String aChar : chars) {
                list.add(aChar);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ThreadNotify3 threadNotify3 = new ThreadNotify3();
        executorService.submit(threadNotify3.new B());
        executorService.submit(threadNotify3.new A());
        executorService.shutdown();
    }

}
