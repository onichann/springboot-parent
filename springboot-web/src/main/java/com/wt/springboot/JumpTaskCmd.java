package com.wt.springboot;

import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.engine.ActivitiEngineAgenda;
import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.*;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author wutong
 * @date 2023/1/17 10:50
 * 说明:
 */
public class JumpTaskCmd implements Command<Void> {

    /**
     * 当前流程实例的待办节点
     * 当多实例节点为并行时，会有多个节点
     * 跳过多实例节点时，需要把它们都删除
     */
    private List<String> taskIds;

    /**
     * 以下两个节点，targetNodeId跳过过去的节点，即将为代办任务节点
     * var 是targetNodeId节点需要的参数，如：候选人、多实例完成率等等
     */
    private String targetNodeId;
    private Map<String, Object> vars;

    public JumpTaskCmd(List<String> taskIds, String targetNodeId, Map<String, Object> vars) {
        this.taskIds = taskIds;
        this.targetNodeId = targetNodeId;
        this.vars = vars;
    }



    @Override
    public Void execute(CommandContext commandContext) {
        //获取其中一个代办任务
        String taskId = taskIds.get(0);
        //任务执行管理器
        TaskEntityManager taskEntityManager = commandContext.getTaskEntityManager();
        ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
        TaskEntity taskEntity = taskEntityManager.findById(taskId);
        //流程定义对象信息
        String processDefinitionId = taskEntity.getProcessDefinitionId();
        Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
        //节点的元信息对象，封装了节点的元数据，如入线和出线
        Activity flowElement = (Activity) process.getFlowElement(taskEntity.getTaskDefinitionKey());

        // 执行实例对象
        //--修改成父级执行对象 2023.02.24
        String executionId = taskEntity.getExecutionId();
        ExecutionEntity currentExecutionEntity = executionEntityManager.findById(executionId);

        Object behavior = flowElement.getBehavior();
        //判断当前节点的行为类，即是否多实例任务类型，主要做两件事，删除2级实例下的三级实例，然后重新设置2级实例的信息执行更新操作
        if (behavior instanceof MultiInstanceActivityBehavior) {
            HistoricTaskInstanceEntityManager historicTaskInstance = commandContext.getHistoricTaskInstanceEntityManager();
            this.taskIds.forEach(historicTaskInstance::delete);

            ExecutionEntity parentExecutionEntity = currentExecutionEntity.getParent();
            executionEntityManager.deleteChildExecutions(parentExecutionEntity, "jump task");
            parentExecutionEntity.setActive(true);
            parentExecutionEntity.setMultiInstanceRoot(false);
            executionEntityManager.update(parentExecutionEntity);
            currentExecutionEntity = parentExecutionEntity;
        } else {
            //不是多实例
            //删除历史任务节点
            HistoricTaskInstanceEntityManager historicTaskInstance = commandContext.getHistoricTaskInstanceEntityManager();
            historicTaskInstance.delete(taskId);
            //删除当前的任务并把条件删掉
            IdentityLinkEntityManager identityLinkEntityManager = commandContext.getIdentityLinkEntityManager();
            identityLinkEntityManager.deleteIdentityLinksByTaskId(taskId);
            //taskEntityManager.delete(taskId); 报外键冲突，执行前面的代码
            taskEntityManager.deleteTask(taskEntity, "jumpReason", false, false);
        }
        //使用agenda驱动实例的继续运转，即跳入到目标节点
        ActivitiEngineAgenda agenda = commandContext.getAgenda();

        // 如果目标节点为结束节点（不是用户节点）
        if ("end".equals(targetNodeId)) {
            List<EndEvent> endEventList = process.findFlowElementsOfType(EndEvent.class);
//            FlowElement targetFlowElement = process.getFlowElement(targetNodeId);
            currentExecutionEntity.setCurrentFlowElement(endEventList.get(0));
            agenda.planEndExecutionOperation(currentExecutionEntity);
            return null;
        }

        // 目标节点处理
        Activity targetFlowElement = (Activity) process.getFlowElement(targetNodeId);
        behavior = targetFlowElement.getBehavior();

        // 当目标节点为多实例节点时，需要从执行实例中获取它们父实例对象
        if (behavior instanceof MultiInstanceActivityBehavior) {
            //待跳出节点的二级实例对象(多实例下的执行实例对象为三级实例对象)
            ExecutionEntity secondExecutionEntity = currentExecutionEntity.getParent();
            secondExecutionEntity.setCurrentFlowElement(targetFlowElement);
            if (!Objects.isNull(vars) && vars.size() > 0) {// 如需要提供的参数
                secondExecutionEntity.setVariables(vars);
            }
            agenda.planContinueMultiInstanceOperation(secondExecutionEntity);
            return null;
        }

        if (!Objects.isNull(vars) && vars.size() > 0) {
            currentExecutionEntity.setVariables(vars);
        }
        currentExecutionEntity.setCurrentFlowElement(targetFlowElement);
        agenda.planContinueProcessInCompensation(currentExecutionEntity);

        return null;
    }
}
