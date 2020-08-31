package com.wt.activiti.task;


import com.wt.activiti.pagination.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2020-03-23 上午 9:59
 * description
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired(required = false)
    private TaskService taskService;

    @GetMapping("/waitTask")
    public IPage<TaskInfo> getWaitTask(@RequestParam("userId") String userId,
                                       @RequestParam("page")int page,
                                       @RequestParam("rows")int rows) {
        return taskService.getWaitTask(userId, page, rows);
    }

    @GetMapping("/completeTask")
    public void completeTask(@RequestParam Map map) {
        taskService.completeTask((String) map.get("taskId"),map);
    }

    @GetMapping("/getNextTaskDefinition")
    public TaskDefinition getNextTaskDefinition(String processInstanceId, String taskId) {
        return taskService.getNextTaskDefinition(processInstanceId, taskId);
    }

    @GetMapping("/getPreviousTaskDefinition")
    public TaskDefinition getPreviousTaskDefinition(String processDefId, String taskDefinitionKey) {
        return taskService.getPreviousTaskDefinition(processDefId, taskDefinitionKey);
    }

    @GetMapping("/claim")
    public void claimTask(String taskId, String userId) {
        taskService.claimTask(taskId, userId);
    }

    @GetMapping("/getNextTasks")
    public List<TaskInfo> getNextTasks(String processInstanceId) {
        return taskService.getNextTasks(processInstanceId);
    }
}
