package com.wt.springboot.executor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/**
 * @author Administrator
 * @date 2019-05-15 上午 11:06
 * PROJECT_NAME springboot-parent
 */
public class ExecutorDemo {

    public static void scheduledTreadPool(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);

        pool.schedule(() -> System.out.println("延迟3s调用"), 3, TimeUnit.SECONDS);
        pool.scheduleAtFixedRate(() -> System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+" 定时每3s调用"), 1,3, TimeUnit.SECONDS);
        //pool.shutdown();

    }

    public static void test() throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println("主线程："+Thread.currentThread().getName());

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        //1.
        executorService.submit(() -> System.out.println("子线程："+Thread.currentThread().getName()));
        //2.
        FutureTask<String> stringFutureTask = new FutureTask<>(() -> "yes");
        Future<?> submit = executorService.submit(stringFutureTask);
        System.out.println(stringFutureTask.isDone()+"---------------");
        System.out.println("submit="+stringFutureTask.get());
        System.out.println(stringFutureTask.isDone()+"-------------------");
        //3
        ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService<>(executorService);
        executorCompletionService.submit(() -> "yes2");
        System.out.println("submit="+executorCompletionService.take().get());
        //关闭
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(2000, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    public static void main(String[] args) {
        scheduledTreadPool();

    }
}
