package com.wt.springboot.async;

import com.google.common.collect.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Controller
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/page")
    @ResponseBody
    public void page(){
        System.out.println("主线程："+Thread.currentThread().getName());
        asyncService.generateReport();
    }

    @RequestMapping("/page2")
    @ResponseBody
    public void page2() throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println("主线程："+Thread.currentThread().getName());

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        //1.
        executorService.submit(() -> System.out.println("子线程："+Thread.currentThread().getName()));
        //2.
        FutureTask<String> stringFutureTask = new FutureTask<>(() -> "yes");
        Future<?> submit = executorService.submit(stringFutureTask);
        while (!stringFutureTask.isDone()){
            if(stringFutureTask.isDone()){
                System.out.println("submitDone="+stringFutureTask.get());
                break;
            }
        }
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

    public static void main(String[] args) throws  Exception{
       // new AsyncController().page2();
        System.out.println(URLDecoder.decode("tbbh=S2019050600001&ajly=&bsqd=%E7%A7%BB%E5%8A%A8%E7%AB%AF%E4%B8%8A%E6%8A%A5&szq=19&szz=19516&ytbmj=96825.86&zdmj=96825.86&sbr=%E8%AE%B8%E4%BF%8A&cjsj=2018-01-23&xsqk=&distance=111","UTF-8"));
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        integers.sort(Ordering.natural().reversed());
        System.out.println(integers);
    }
}
