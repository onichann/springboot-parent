package com.wt.springboot.core;

import cn.hutool.http.HttpUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.concurrent.TimeUnit;

/**
 * @author Pat.Wu
 * @date 2022/1/27 11:56
 * @description
 */
@Service
public class AsyncService {

    public ListenableFuture<String> asyncFromUrl() {
        SettableListenableFuture<String> future = new SettableListenableFuture<>();
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName()+"---");
            String result = HttpUtil.get("https://blog.csdn.net/phoenix/web/v1/reward/article-users?articleId=80372711");
            future.set(result);
        } catch (Exception e) {
            future.setException(e);
        }
        return future;
    }
}
