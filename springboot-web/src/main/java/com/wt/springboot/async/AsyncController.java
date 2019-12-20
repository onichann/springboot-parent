package com.wt.springboot.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
