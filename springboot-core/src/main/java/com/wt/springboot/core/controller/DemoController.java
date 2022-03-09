package com.wt.springboot.core.controller;

import com.wt.springboot.core.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Pat.Wu
 * @date 2022/1/27 14:21
 * @description
 */
@RestController
@Slf4j
public class DemoController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/demo")
    public String getFromRemoteService() throws ExecutionException, InterruptedException {
        ListenableFuture<String> future = asyncService.asyncFromUrl();
        future.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onFailure(Throwable ex) {
                throw new RuntimeException(ex);
            }

            @Override
            public void onSuccess(String result) {
                System.out.println(result+System.currentTimeMillis());
            }
        });
        System.out.println(Thread.currentThread().getName()+"--out11");
        CompletableFuture<String> completable = future.completable();
        System.out.println(completable.get()+"--compleatbleFuture");
        completable.handle(new BiFunction<String, Throwable, Object>() {
            @Override
            public Object apply(String s, Throwable throwable) {
                return null;
            }
        }).exceptionally(new Function<Throwable, Object>() {
            @Override
            public Object apply(Throwable throwable) {
                return null;
            }
        }).thenApply(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
                return null;
            }
        }).join();
        String s = completable.get();
        log.info("lombok log");
        return future.get();
    }

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/lock")
    public void testLock() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RLock lock = redissonClient.getLock("lock:test");
                    try {
                        System.out.println(Thread.currentThread().getName()+ "-"+finalI +"等待获取锁");
                        countDownLatch.await();
                        lock.lock();
                        System.out.println(Thread.currentThread().getName()+"-"+finalI+"获取锁到锁,doing");
//                        TimeUnit.SECONDS.sleep(40);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        System.out.println(Thread.currentThread().getName()+"-"+finalI+"释放锁到锁");
                        lock.unlock();
                    }
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(2);
        countDownLatch.countDown();
    }

    public static void main(String[] args) {
        String a = "s";
        String b = new String("s");
        System.out.println(a==b);
        System.out.println(a.equals(b));
    }
}
