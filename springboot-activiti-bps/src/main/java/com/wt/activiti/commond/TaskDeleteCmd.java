package com.wt.activiti.commond;

import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManagerImpl;

/**
 * @author Administrator
 * @date 2020-03-23 下午 4:09
 * description
 */
public class TaskDeleteCmd extends NeedsActiveTaskCmd<String> {

    public TaskDeleteCmd(String taskId) {
        super(taskId);
    }

    @Override
    protected String execute(CommandContext commandContext, TaskEntity currentTask) {
        TaskEntityManagerImpl taskEntityManager = (TaskEntityManagerImpl) commandContext.getTaskEntityManager();
        ExecutionEntity executionEntity = currentTask.getExecution();
        taskEntityManager.deleteTask(currentTask, "jump", false, false);
        return executionEntity.getId();
    }
}
