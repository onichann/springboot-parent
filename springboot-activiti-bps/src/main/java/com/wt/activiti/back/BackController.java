package com.wt.activiti.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2020-03-23 下午 4:21
 * description
 */
@RestController
@RequestMapping("/back")
public class BackController {

    @Autowired(required = false)
    private BackService backService;

    @GetMapping("/backTask")
    public void backTask(String taskId){
        backService.backTask(taskId);
    }
}
