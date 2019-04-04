package com.wt.springboot.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        executorService.submit(() -> System.out.println("子线程："+Thread.currentThread().getName()));
        FutureTask<String> stringFutureTask = new FutureTask<>(() -> "yes");
        Future<?> submit = executorService.submit(stringFutureTask);
//        TimeUnit.SECONDS.sleep(10);
//        System.out.println("11111----"+submit.get());
        while (!stringFutureTask.isDone()){
            if(stringFutureTask.isDone()){
                System.out.println("submitDone="+stringFutureTask.get());
                break;
            }
        }
        System.out.println(stringFutureTask.isDone()+"---------------");
        System.out.println("submit="+stringFutureTask.get());
        System.out.println(stringFutureTask.isDone()+"-------------------");
        ExecutorCompletionService executorCompletionService = new ExecutorCompletionService(executorService);
        executorCompletionService.submit(() -> "yes2");
        System.out.println("submit="+executorCompletionService.take().get());
        executorService.shutdown();
    }
}