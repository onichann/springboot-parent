package com.wt.springboot.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
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
        ExecutorCompletionService executorCompletionService = new ExecutorCompletionService(executorService);
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
        System.out.println(URLDecoder.decode("%E5%9C%B0%E5%9D%97%E5%90%8D%E7%A7%B0","UTF-8"));
    }
}
