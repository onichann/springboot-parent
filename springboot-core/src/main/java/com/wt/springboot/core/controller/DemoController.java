package com.wt.springboot.core.controller;

import com.wt.springboot.core.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Pat.Wu
 * @date 2022/1/27 14:21
 * @description
 */
@RestController
//@Slf4j
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
        System.out.println(Thread.currentThread().getName()+"--out");
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
        return future.get();
    }
}
