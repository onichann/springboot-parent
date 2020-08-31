package com.wt.activiti.task;


import com.wt.activiti.pagination.IPage;
import com.wt.activiti.pagination.Page;
import org.activiti.bpmn.model.*;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.delegate.ActivityBehavior;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2020-03-19 下午 1:28
 * description
 */
public class TaskServiceImpl implements TaskService {

    private static final Log logger = LogFactory.getLog(TaskServiceImpl.class);

    @Autowired
    private org.activiti.engine.TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public IPage<TaskInfo> getWaitTask(String userId, int page, int rows) {
        int count = (int)taskService.createTaskQuery().taskCandidateOrAssigned(userId).count();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(userId).orderByTaskCreateTime().desc().listPage(page - 1, rows);
        List<TaskInfo> taskInfos = convertToTaskInfo(tasks);
        IPage<TaskInfo> iPage = new Page<>();
        iPage.setCurrent(page);
        iPage.setSize(rows);
        iPage.setTotal(count);
        iPage.setRecords(taskInfos);
        return iPage;
    }

    @Override
    public void claimTask(String taskId, String userId) {
        taskService.claim(taskId,userId);
    }

    @Override
    public void unClaimTask(String taskId) {
        taskService.unclaim(taskId);
    }

    @Override
    public void delegateTask(String taskId, String userId) {
        taskService.delegateTask(taskId, userId);
    }

    @Override
    public void resolveTask(String taskId) {
        taskService.resolveTask(taskId,null);
    }

    @Override
    public void resolveTask(String taskId, Map<String, Object> variables) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task.getOwner() != null) {
            DelegationState delegationState = task.getDelegationState();
            if (DelegationState.RESOLVED.equals(delegationState)) {
                throw new RuntimeException("此委托任务已是完结状态");
            } else if (DelegationState.PENDING.equals(delegationState)) {
                taskService.resolveTask(taskId, variables);
            }else{
                throw new RuntimeException("此任务不是委托任务");
            }
        }else{
            throw new RuntimeException("此任务不是委托任务");
        }
    }

    @Override
    public void completeTask(String taskId) {
        completeTask(taskId, null);
    }

    @Override
    public void completeTask(String taskId, Map<String, Object> variables) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (DelegationState.PENDING.equals(task.getDelegationState())) {
            throw new RuntimeException("请先完成委托任务");
        }
        taskService.complete(taskId,variables);
    }

    @Override
    public TaskDefinition getNextTaskDefinition(String processInstanceId, String taskId) {
        TaskDefinition taskDefinition = new TaskDefinition();
        if (ObjectUtils.isEmpty(processInstanceId) || ObjectUtils.isEmpty(taskId)) {
            throw new RuntimeException("processInstanceId:[" + processInstanceId + "]为空 or taskId:[\" + taskId + \"]为空，获取候选组失败!");
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            throw new RuntimeException("processInstanceId:[" + processInstanceId + "]流程不存在，获取候选组失败!");
        } else {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            if (task == null) {
                throw new RuntimeException("taskId:[" + taskId + "]任务不存在，获取候选组失败!");
            } else {
                String taskDefinitionKey = task.getTaskDefinitionKey();
                if (taskDefinitionKey == null) {
                    logger.debug("taskId:[" + taskId + "]任务节点定义key为空，获取候选组失败!");
                } else {
                    BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
                    FlowNode activityImpl = (FlowNode)bpmnModel.getMainProcess().getFlowElement(taskDefinitionKey);
                    ActivityBehavior behavior = (ActivityBehavior)activityImpl.getBehavior();
                    if (!(behavior instanceof UserTaskActivityBehavior)) {
                        throw new RuntimeException("processInstanceId:[" + processInstanceId + "] , taskId:[" + taskId + "] 节点类型不支持获取候选组!");
                    }else{
                        List<SequenceFlow> outgoingFlows = activityImpl.getOutgoingFlows();
                        if (CollectionUtils.isEmpty(outgoingFlows)) {
                            throw new RuntimeException("processInstanceId:[" + processInstanceId + "] , taskId:[" + taskId + "] 未找到当前任务的流向信息，获取候选组!");
                        }else{
                            if (outgoingFlows.size() > 1) {
                                throw new RuntimeException("processInstanceId:[" + processInstanceId + "] , taskId:[" + taskId + "] 当前任务有多个流向信息，不支持获取候选组!");
                            }else{
                                SequenceFlow sequenceFlow = outgoingFlows.get(0);
                                FlowNode targetActivityImpl = (FlowNode)sequenceFlow.getTargetFlowElement();
                                if (!(targetActivityImpl.getBehavior() instanceof UserTaskActivityBehavior)) {
                                    throw new RuntimeException("processInstanceId:[" + processInstanceId + "] , taskId:[" + taskId + "] 当前任务的下个任务节点类型不支持获取候选组!");
                                }else{
                                    taskDefinition.setId(targetActivityImpl.getId());
                                    taskDefinition.setName(targetActivityImpl.getName());
                                    taskDefinition.setAssignee(((UserTask) targetActivityImpl).getAssignee());
                                    taskDefinition.setCandidateUsers(((UserTask) targetActivityImpl).getCandidateUsers());
                                    taskDefinition.setCandidateGroups(((UserTask) targetActivityImpl).getCandidateGroups());
                                }
                            }
                        }
                    }
                }
            }
        }
        return taskDefinition;
    }

    @Override
    public List<TaskDefinition> getNextTaskDefinition(String processDefId, String taskDefID, Map<String, Object> variables) {
        if (ObjectUtils.isEmpty(processDefId) || ObjectUtils.isEmpty(taskDefID)) {
            throw new RuntimeException("processDefId:[" + processDefId + "]为空 or taskDefID:[\" + taskDefinitionKey + \"]为空!");
        }
        List<TaskDefinition> taskDefList = new ArrayList<>();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefId);
        FlowNode activityImpl = (FlowNode)bpmnModel.getMainProcess().getFlowElement(taskDefID);
        if (!(activityImpl instanceof UserTask)) {
            throw new RuntimeException("节点不是用户任务，不支持查询!");
        }
        List<SequenceFlow> outgoingFlows = activityImpl.getOutgoingFlows();
        //并行网管
        if(outgoingFlows.size()==1&&(outgoingFlows.get(0).getTargetFlowElement() instanceof ParallelGateway)){
            List<SequenceFlow> parallelGatewaySeqFlow = ((FlowNode) outgoingFlows.get(0).getTargetFlowElement()).getOutgoingFlows();
            Iterator<SequenceFlow> iterator = parallelGatewaySeqFlow.iterator();
            while (iterator.hasNext()) {
                SequenceFlow next = iterator.next();
                FlowElement targetFlowElement = next.getTargetFlowElement();
                if (targetFlowElement instanceof UserTask) {
                    TaskDefinition taskDefinition = getTaskDefinition(next, targetFlowElement);
                    taskDefList.add(taskDefinition);
                }
            }
        }
        if(outgoingFlows.size()==1&&(outgoingFlows.get(0).getTargetFlowElement() instanceof UserTask)){
            FlowElement targetFlowElement=outgoingFlows.get(0).getTargetFlowElement();
            if (targetFlowElement instanceof UserTask) {
                TaskDefinition taskDefinition = getTaskDefinition(outgoingFlows.get(0), targetFlowElement);
                taskDefList.add(taskDefinition);
            }
        }
        return taskDefList;
    }

    private TaskDefinition getTaskDefinition(SequenceFlow seqFlow, FlowElement flowElement) {
        TaskDefinition taskDefinition = new TaskDefinition();
        taskDefinition.setId(flowElement.getId());
        taskDefinition.setName(flowElement.getName());
        taskDefinition.setAssignee(((UserTask) flowElement).getAssignee());
        taskDefinition.setCandidateUsers(((UserTask) flowElement).getCandidateUsers());
        taskDefinition.setCandidateGroups(((UserTask) flowElement).getCandidateGroups());
        taskDefinition.setConditionExpression(seqFlow.getConditionExpression());
        return taskDefinition;
    }

    @Override
    public TaskDefinition getPreviousTaskDefinition(String processDefId, String taskDefinitionKey) {
        TaskDefinition taskDefinition = new TaskDefinition();
        if (ObjectUtils.isEmpty(processDefId) || ObjectUtils.isEmpty(taskDefinitionKey)) {
            throw new RuntimeException("processDefId:[" + processDefId + "]为空 or taskDefinitionKey:[\" + taskDefinitionKey + \"]为空!");
        }
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefId);
        FlowNode activityImpl = (FlowNode)bpmnModel.getMainProcess().getFlowElement(taskDefinitionKey);
        if (!(activityImpl instanceof UserTask)) {
            throw new RuntimeException("节点不是用户任务，不支持查询!");
        }
        List<SequenceFlow> incomingFlows = activityImpl.getIncomingFlows();
        if (CollectionUtils.isEmpty(incomingFlows)) {
            return null;
        }else{
            SequenceFlow sequenceFlow = incomingFlows.stream().filter(flow -> flow.getConditionExpression() == null).findFirst().orElse(null);
            if (sequenceFlow != null) {
                FlowElement sourceFlowElement = sequenceFlow.getSourceFlowElement();
                if (sourceFlowElement instanceof UserTask) {
                    taskDefinition.setId(sourceFlowElement.getId());
                    taskDefinition.setName(sourceFlowElement.getName());
                    taskDefinition.setAssignee(((UserTask) sourceFlowElement).getAssignee());
                    taskDefinition.setCandidateUsers(((UserTask) sourceFlowElement).getCandidateUsers());
                    taskDefinition.setCandidateGroups(((UserTask) sourceFlowElement).getCandidateGroups());
                }
            }
        }
        return taskDefinition;
    }

    @Override
    public List<TaskDefinition> getPreviousTaskDefinitions(String processDefId, String taskDefinitionKey) {
        if (ObjectUtils.isEmpty(processDefId) || ObjectUtils.isEmpty(taskDefinitionKey)) {
            throw new RuntimeException("processDefId:[" + processDefId + "]为空 or taskDefinitionKey:[\" + taskDefinitionKey + \"]为空!");
        }
        List<TaskDefinition> taskDefList = new ArrayList<>();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefId);
        FlowNode activityImpl = (FlowNode)bpmnModel.getMainProcess().getFlowElement(taskDefinitionKey);
        if (!(activityImpl instanceof UserTask)) {
            throw new RuntimeException("节点不是用户任务，不支持查询!");
        }
        List<SequenceFlow> incomingFlows = activityImpl.getIncomingFlows();
        //并行网关
        if(incomingFlows.size()==1&&(incomingFlows.get(0).getSourceFlowElement() instanceof ParallelGateway)){
            List<SequenceFlow> parallelGatewaySeqFlow = ((FlowNode) incomingFlows.get(0).getSourceFlowElement()).getIncomingFlows();
            Iterator<SequenceFlow> iterator = parallelGatewaySeqFlow.iterator();
            while (iterator.hasNext()) {
                SequenceFlow next = iterator.next();
                FlowElement sourceFlowElement = next.getSourceFlowElement();
                if (sourceFlowElement instanceof UserTask) {
                    TaskDefinition taskDefinition = getTaskDefinition(next, sourceFlowElement);
                    taskDefList.add(taskDefinition);
                }
            }
        }
        if(incomingFlows.size()==1&&(incomingFlows.get(0).getSourceFlowElement() instanceof UserTask)){
            FlowElement sourceFlowElement = incomingFlows.get(0).getSourceFlowElement();
            if (sourceFlowElement instanceof UserTask) {
                TaskDefinition taskDefinition = getTaskDefinition(incomingFlows.get(0), sourceFlowElement);
                taskDefList.add(taskDefinition);
            }
        }

        return taskDefList;
    }

    @Override
    public boolean isLastUserTask(String processDefId, String taskDefinitionKey) {
        if (ObjectUtils.isEmpty(processDefId) || ObjectUtils.isEmpty(taskDefinitionKey)) {
            throw new RuntimeException("processDefId:[" + processDefId + "]为空 or taskDefinitionKey:[\" + taskDefinitionKey + \"]为空!");
        }
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefId);
        FlowNode activityImpl = (FlowNode)bpmnModel.getMainProcess().getFlowElement(taskDefinitionKey);
        if (!(activityImpl instanceof UserTask)) {
            throw new RuntimeException("节点不是用户任务，不支持查询!");
        }
        List<SequenceFlow> outgoingFlows = activityImpl.getOutgoingFlows();
        if (outgoingFlows.size() == 1 && outgoingFlows.get(0).getConditionExpression() == null && outgoingFlows.get(0).getTargetFlowElement() instanceof EndEvent) {
            return true;
        }
        return false;
    }

    @Override
    public List<TaskInfo> getNextTasks(String processInstanceId) {
        if (!StringUtils.hasText(processInstanceId)) {
            return null;
        }
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        return convertToTaskInfo(taskList);
    }

    private List<TaskInfo> convertToTaskInfo(List<Task> tasks) {
        List<TaskInfo> taskInfos = new ArrayList<>();
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            TaskInfo taskInfo = new TaskInfo();
            Task task = it.next();
            BeanUtils.copyProperties(task, taskInfo);
            taskInfo.setDelegationState(task.getDelegationState()!=null?task.getDelegationState().name():null);
            taskInfos.add(taskInfo);
        }
        return taskInfos;
    }
}
