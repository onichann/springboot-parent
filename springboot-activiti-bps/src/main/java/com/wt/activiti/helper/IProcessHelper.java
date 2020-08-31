package com.wt.activiti.helper;

/**
 * @author Administrator
 * @date 2020-07-07 上午 11:43
 * introduction
 */
public interface IProcessHelper {
    /**
     * 流程实例在流程定义中等待的位置
     * @param processInstanceId 流程实例ID
     * @return 流程实例元信息
     */
    ProcessInstanceMeta getProcessInstanceMeta(String processInstanceId);
}
