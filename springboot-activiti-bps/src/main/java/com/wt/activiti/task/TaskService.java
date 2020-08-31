package com.wt.activiti.task;


import com.wt.activiti.pagination.IPage;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2020-03-19 上午 11:15
 * description
 */
public interface TaskService {

    /**
     * 待办任务
     * @param userId
     * @param page
     * @param rows
     * @return
     */
    IPage<TaskInfo> getWaitTask(String userId, int page, int rows);

    /**
     * 签收任务
     * @param taskId
     * @param userId
     */
    void claimTask(String taskId, String userId);

    /**
     * 放弃任务
     * @param taskId
     */
    void unClaimTask(String taskId);


    /**
     * 委托任务
     * @param taskId
     * @param userId
     */
    void delegateTask(String taskId, String userId);

    /**
     * 完成委托任务
     * @param taskId
     */
    void resolveTask(String taskId);

    void resolveTask(String taskId, Map<String, Object> variables);

    /**
     * 完成任务
     * @param taskId
     */
    void completeTask(String taskId);

    void completeTask(String taskId, Map<String, Object> variables);

    /**
     * 获取下个活动定义
     * @param processInstanceId
     * @param taskId
     * @return
     */
    @Deprecated
    TaskDefinition getNextTaskDefinition(String processInstanceId, String taskId);

    /**
     *获取下个活动定义
     * @param processDefId
     * @param taskDefID
     * @param variables
     * @return
     */
    List<TaskDefinition> getNextTaskDefinition(String processDefId, String taskDefID, Map<String, Object> variables);

    /**
     * 获取上个活动定义
     * @param processDefId
     * @param taskDefinitionKey
     * @return
     */
    @Deprecated
    TaskDefinition getPreviousTaskDefinition(String processDefId, String taskDefinitionKey);


    /**
     * 获取上个活动定义
     * @param processDefId
     * @param taskDefinitionKey
     * @return
     */
    List<TaskDefinition> getPreviousTaskDefinitions(String processDefId, String taskDefinitionKey);

    /**
     * 是否是最后一个用户节点
     * @param processDefId
     * @param taskDefinitionKey
     * @return
     */
    boolean isLastUserTask(String processDefId, String taskDefinitionKey);

    /**
     *
     * @param processInstanceId
     * @return
     */
    List<TaskInfo> getNextTasks(String processInstanceId);
}
