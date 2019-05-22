package com.wt.springboot.executor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    public static void main(String[] args) {
        scheduledTreadPool();
    }
}
