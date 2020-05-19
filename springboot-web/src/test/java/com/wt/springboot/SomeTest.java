package com.wt.springboot;


import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2019-07-23 下午 3:38
 * PROJECT_NAME webProject
 */
public class SomeTest {
    @NotNull String foo(int x, @NotNull String data){
       return x > 0 ? data : "";
   }

    public static void main(String[] args) {
        StringBuilder result = new StringBuilder();
        List messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = (List<CompletableFuture>) messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> StringUtils.upperCase((String) s)))
                .collect(Collectors.toList());
        CompletableFuture allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .whenComplete((v, th) -> {
                    result.append("done");
                    futures.forEach(v1-> System.out.println(v1.getNow(null)));
                });
        //allOf.join();
        System.out.println("done");

        CompletableFuture.completedFuture("ss").thenApplyAsync(String::toUpperCase).whenComplete((v,e)-> System.out.println(v));
    }
}
