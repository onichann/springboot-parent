package com.wt.activiti.helper;


import java.util.List;

/**
 * @author Administrator
 * @date 2020-07-07 上午 11:42
 * introduction
 */
public class ProcessInstanceMetaImpl implements ProcessInstanceMeta {

    private String processInstanceId;
    private List<String> activeActivitiesIds;

    public ProcessInstanceMetaImpl() {
    }

    public ProcessInstanceMetaImpl(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public List<String> getActiveActivitiesIds() {
        return activeActivitiesIds;
    }

    public void setActiveActivitiesIds(List<String> activeActivitiesIds) {
        this.activeActivitiesIds = activeActivitiesIds;
    }
}
