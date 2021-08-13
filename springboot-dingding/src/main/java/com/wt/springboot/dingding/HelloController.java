package com.wt.springboot.dingding;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhiwen.zuo
 * @date 2021-07-26
 **/
@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {
    private final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern(getClass().getName()).build());
    private Future<?> future;
    private AtomicReference<Future<?>> lastTask = new AtomicReference<>(null);

    public HelloController() {
        scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
//        lastTask.compareAndSet(null, scheduledThreadPoolExecutor.scheduleWithFixedDelay(this::run, 0, 1, TimeUnit.NANOSECONDS));
//        log.info("future : {}", lastTask.get().hashCode());
    }

    @GetMapping
    public Object index() {
        return System.currentTimeMillis();
    }

    private AtomicBoolean taskStatus = new AtomicBoolean(false);

    @GetMapping("/start")
    public Object start() {
        if (taskStatus.compareAndSet(false, true)) {
            lastTask.compareAndSet(null, scheduledThreadPoolExecutor.scheduleWithFixedDelay(this::run, 0, 1, TimeUnit.NANOSECONDS));
        }
        return true;
    }

    @GetMapping("/stop")
    public Object stop() {
        Future<?> lastTask = this.lastTask.getAndSet(null);
        if (lastTask != null) {
            lastTask.cancel(true);
        }
        return true;
    }


    private void run() {
        try {
            while (true) {
                log.info("{} run task", Thread.currentThread().getId());
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            log.info(e.getMessage());
        }
    }
}
