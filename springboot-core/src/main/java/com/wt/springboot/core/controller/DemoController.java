package com.wt.springboot.core.controller;

import com.wt.springboot.core.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
                log.info(result+System.currentTimeMillis());
            }
        });
        System.out.println(Thread.currentThread().getName()+"--out");
        CompletableFuture<String> completable = future.completable();
        System.out.println(completable.get()+"--compleatbleFuture");
        return future.get();
    }
}
