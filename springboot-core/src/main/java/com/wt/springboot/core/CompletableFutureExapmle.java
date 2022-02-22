package com.wt.springboot.core;

import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Pat.Wu
 * @date 2022/2/22 14:56
 * @description
 */
public class CompletableFutureExapmle {

    @SneakyThrows
    public static void main(String[] args) {
        CompletableFuture<String> completedFuture = CompletableFuture.completedFuture("value");

        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> System.out.println("runAsync"));

        String join = CompletableFuture.supplyAsync(() -> "supplyAsync").join();
        System.out.println(join);

        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> "supplyAsync");
        CompletableFuture<Object> thenApply = supplyAsync.thenApply(new Function<String, Object>() {
            @Override
            public Object apply(String s) {
                return s + "+ thenApply";
            }
        });
        System.out.println(thenApply.get());
        System.out.println(supplyAsync.equals(thenApply));

        supplyAsync.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s+"+ thenAccept");
            }
        });

        supplyAsync.thenRun(new Runnable() {
            @Override
            public void run() {
                System.out.println("supplyAsync -> thenRun");
            }
        });

        CompletableFuture<String> cfA = CompletableFuture.supplyAsync(() -> {
            return "hello";
        });
        CompletableFuture<String> cfB = CompletableFuture.supplyAsync(() -> {
            return " world";
        });
        CompletableFuture<String> cfC = CompletableFuture.supplyAsync(() -> {
            return ", I'm CodingTao!";
        });

        CompletableFuture<Object> thenCombine = cfA.thenCombine(cfB, new BiFunction<String, String, Object>() {
            @Override
            public Object apply(String s, String s2) {
                return s + "+" + s2;
            }
        });
        System.out.println("thenCombine: "+ thenCombine.get());

        CompletableFuture<Void> voidCompletableFuture = cfA.thenAcceptBoth(cfB, new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String s2) {
                System.out.println("thenAcceptBoth: " + s + "+" + s2);
            }
        });
        System.out.println(voidCompletableFuture.get());

        cfA.runAfterBoth(cfB, new Runnable() {
            @Override
            public void run() {
                System.out.println("任务A和任务B同时完成");
            }
        });


        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "通过方式A获取商品a";
        });
        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "通过方式B获取商品a";
        });
        CompletableFuture<String> futureC = futureA.applyToEither(futureB, product -> "applyToEither结果:" + product);
        //结果:通过方式A获取商品a
        System.out.println(futureC.get());


        CompletableFuture<Void> futureD = futureA.acceptEither(futureB, new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("acceptEither结果:" + s);
            }
        });

        futureA.runAfterEither(futureB, new Runnable() {
            @Override
            public void run() {
                System.out.println("runAfterEither");
            }
        });

        //thenCompose 当原任务完成后，以其结果为参数，返回一个新的任务（而不是新结果，类似flatMap）
        String original = "Message";
        CompletableFuture<String> thenCompose = CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).thenCompose(upper -> CompletableFuture.completedFuture(original)
                .thenApply(String::toLowerCase).thenApply(s -> upper + s));
        System.out.println(thenCompose.join());

        //handle 任务完成后执行BiFunction，结果转换，入参为结果或者异常，返回新结果
        // whenComplete 任务完成后执行BiConsumer，结果消费，入参为结果或者异常，不返回新结果
        // exceptionally 任务异常，则执行Function，异常转换，入参为原任务的异常信息，若原任务无异常，则返回原任务结果，即不执行转换

        CompletableFuture.supplyAsync(() -> "resultA")
                .thenApply(resultA -> resultA + " resultB")
                .thenApply(resultB -> resultB + " resultC")
                .thenApply(resultC -> resultC + " resultD");


        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "resultA")
                .thenApply(resultA -> resultA + " resultB")
                // 任务 C 抛出异常
                .thenApply(resultB -> {throw new RuntimeException();})
                // 处理任务 C 的返回值或异常
                .handle(new BiFunction<Object, Throwable, Object>() {
                    @Override
                    public Object apply(Object re, Throwable throwable) {
                        if (throwable != null) {
                            return "errorResultC";
                        }
                        return re;
                    }
                })
                .thenApply(resultC -> {
                    System.out.println("resultC:" + resultC);
                    return resultC + " resultD";
                });
        System.out.println(future.join());


        // whenComplete 任务完成后执行BiConsumer，结果消费，入参为结果或者异常，不返回新结果

        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread()+"job1 start,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if(false){
                throw new RuntimeException("test");
            }else{
                System.out.println(Thread.currentThread()+"job1 exit,time->"+System.currentTimeMillis());
                return 1.2;
            }
        });
        //cf执行完成后会将执行结果和执行过程中抛出的异常传入回调方法
        // 如果是正常执行，a=1.2，b则传入的异常为null
        //如果异常执行，a=null，b则传入异常信息
        CompletableFuture<Double> cf2=cf.whenComplete((a,b)->{
            System.out.println(Thread.currentThread()+"job2 start,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if(b!=null){
                System.out.println("error stack trace->");
                b.printStackTrace();
            }else{
                System.out.println("run succ,result->"+a);
            }
            System.out.println(Thread.currentThread()+"job2 exit,time->"+System.currentTimeMillis());
        });
        //等待子任务执行完成
        System.out.println("main thread start wait,time->"+System.currentTimeMillis());
        //如果cf是正常执行的，cf2.get的结果就是cf执行的结果
        //如果cf是执行异常，则cf2.get会抛出异常
        System.out.println("run result->"+cf2.get());
        System.out.println("main thread exit,time->"+System.currentTimeMillis());


        // exceptionally 任务异常，则执行Function，异常转换，入参为原任务的异常信息，若原任务无异常，则返回原任务结果，即不执行转换

        CompletableFuture<String> exceptionallyFuture = CompletableFuture.supplyAsync(() -> "resultA")
                .thenApply(resultA ->{
                    if (true) {
                        throw new RuntimeException("异常");
                    }
                    return resultA + " resultB";
                } )
                .exceptionally(new Function<Throwable, String>() {
                    @Override
                    public String apply(Throwable throwable) {
                        return String.valueOf(new RuntimeException(throwable));
                    }
                })
                .thenApply(resultB -> resultB + " resultC");
        System.out.println(exceptionallyFuture.join());




    }
}
