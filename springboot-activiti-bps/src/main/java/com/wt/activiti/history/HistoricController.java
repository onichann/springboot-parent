package com.wt.activiti.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 * @date 2020-03-23 下午 2:14
 * description
 */
@RestController
@RequestMapping("/historic")
public class HistoricController {

    @Autowired(required = false)
    private HistoricService historicService;

    @GetMapping("/activityInstance")
    public List<HistoricActivityInstance> queryHistoricActivityInstance(String processInstanceId) {
        return historicService.queryHistoricActivityInstance(processInstanceId);
    }
}
