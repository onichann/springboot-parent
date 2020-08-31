package com.wt.activiti.back;

import com.wt.activiti.commond.BackCommand;
import com.wt.activiti.commond.ExecutionVariableDeleteCmd;
import com.wt.activiti.commond.HQBackCommand;
import com.wt.activiti.commond.TaskDeleteCmd;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator
 * @date 2020-03-23 下午 4:01
 * description
 */
public class BackServiceImpl implements BackService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ManagementService managementService;

    @Autowired
    private HistoryService historyService;

    @Override
    public void backTask(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        managementService.executeCommand(new ExecutionVariableDeleteCmd(task.getExecutionId()));
        managementService.executeCommand(new BackCommand(task));
        managementService.executeCommand(new TaskDeleteCmd(taskId));
        historyService.deleteHistoricTaskInstance(taskId);
    }

    @Override
    public void hqBackTask(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        managementService.executeCommand(new ExecutionVariableDeleteCmd(task.getExecutionId()));
        managementService.executeCommand(new HQBackCommand(task));
        managementService.executeCommand(new TaskDeleteCmd(taskId));
//        historyService.deleteHistoricTaskInstance(taskId);
    }
}
