package com.wt.activiti.commond;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityManager;

import java.util.List;

/**
 * @author Administrator
 * @date 2020-03-23 下午 4:06
 * description
 */
public class ExecutionVariableDeleteCmd implements Command<String> {

    private String executionId;

    public ExecutionVariableDeleteCmd(String executionId) {
        this.executionId = executionId;
    }

    @Override
    public String execute(CommandContext commandContext) {
        VariableInstanceEntityManager vm = commandContext.getVariableInstanceEntityManager();
        List<VariableInstanceEntity> vs = vm.findVariableInstancesByExecutionId(this.executionId);
        for (VariableInstanceEntity v : vs) {
            vm.delete(v);
        }
        return executionId;
    }
}
