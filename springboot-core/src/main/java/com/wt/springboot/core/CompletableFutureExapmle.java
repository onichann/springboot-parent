package com.wt.springboot.core;

import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

//        allOf 合并多个complete为一个，等待全部完成
        System.out.println("--------------allof------------");

        CompletableFuture<Double> cfN = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread()+" start job1,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread()+" exit job1,time->"+System.currentTimeMillis());
            return 1.2;
        });
        CompletableFuture<Double> cfN2 = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread()+" start job2,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            if(true) throw new RuntimeException("test1");
            System.out.println(Thread.currentThread()+" exit job2,time->"+System.currentTimeMillis());
            return 3.2;
        });
        CompletableFuture<Double> cfN3 = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread()+" start job3,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(1300);
            } catch (InterruptedException e) {
            }
            if(true) throw new RuntimeException("test");
            System.out.println(Thread.currentThread()+" exit job3,time->"+System.currentTimeMillis());
            return 2.2;
        });
        //allof等待所有任务执行完成才执行cf4，如果有一个任务异常终止，则cf4.get时会抛出异常，都是正常执行，cf4.get返回null
        //anyOf是只有一个任务执行完成，无论是正常执行或者执行异常，都会执行cf4，cf4.get的结果就是已执行完成的任务的执行结果
        CompletableFuture<Void> cfN4=CompletableFuture.allOf(cfN,cfN2,cfN3).whenComplete((a,b)->{
            if(b!=null){
                System.out.println("error stack trace->");
                b.printStackTrace();
            }else{
                System.out.println("run succ,result->"+a);
            }
        });
        System.out.println("main thread start cfN4.get(),time->"+System.currentTimeMillis());
        //等待子任务执行完成
//        System.out.println("cfN4 run result->"+cfN4.get());
        System.out.println("main thread exit,time->"+System.currentTimeMillis());

        //获取返回值方法：allof()


        System.out.println("---------------anyof---------------------");

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("future1 doing");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of Future 1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("future2 doing");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of Future 2";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("future3 doing");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of Future 3";
        });
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3);
        CompletableFuture<String> stringCompletableFuture = anyOfFuture.thenApply(v -> "done");
        System.out.println(anyOfFuture.get()); // Result of Future 2
        System.out.println("--");


        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(m->{
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return m;
                }).thenApplyAsync(String::toUpperCase))
                .collect(Collectors.toList());
        CompletableFuture<Void> done = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            for (int i = 0; i < futures.size(); i++) {
                CompletableFuture<String> stringCompletableFuture1 = futures.get(i);
            }
            result.append("done");
        });

        CompletableFuture<List<String>> listCompletableFuture = allOf(futures);
        System.out.println(listCompletableFuture.join());
        System.out.println("---end--");
        //由Async 需要join  没有回直接在 then  whenComplete 是完成运行
    }


    public static <T> CompletableFuture<List<T>> allOf(List<CompletableFuture<T>> futuresList) {
        CompletableFuture<Void> allFuturesResult =
                CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
        return allFuturesResult.thenApply(v ->
                futuresList.stream().
                        map(CompletableFuture::join).
                        collect(Collectors.<T>toList())
        );
    }
}
