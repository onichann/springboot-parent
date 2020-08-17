package com.wt.springboot.validator.example;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * @author Administrator
 * @date 2020-04-02 上午 11:52
 * description
 */
public class LcfsVo {

    @Valid
    private GgLzyj lzyj;
    @NotEmpty(message = "type 不能为空，且为 fs或 ht中一种")
    private String type; //fs or ht
    private Map<String, Object> relativeData; //流程变量
    @NotBlank(message = "任务id：rwbId不能为空")
    private String rwbId;
    @NotBlank(message = "流程实例id:processInstId不能为空")
    private String processInstId;
    @NotBlank(message = "流程定义id:processDefId不能为空")
    private String processDefId;
    @NotBlank(message = "任务定义id:taskDefId不能为空")
    private String taskDefId;
    @NotBlank(message = "任务实例id:taskId不能为空")
    private String taskId;
    private Map<String, Object> participants;//下个环节参与者  key: "role"+role  value: userid

    public GgLzyj getLzyj() {
        return lzyj;
    }

    public void setLzyj(GgLzyj lzyj) {
        this.lzyj = lzyj;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getRelativeData() {
        return relativeData;
    }

    public void setRelativeData(Map<String, Object> relativeData) {
        this.relativeData = relativeData;
    }

    public String getRwbId() {
        return rwbId;
    }

    public void setRwbId(String rwbId) {
        this.rwbId = rwbId;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getProcessDefId() {
        return processDefId;
    }

    public void setProcessDefId(String processDefId) {
        this.processDefId = processDefId;
    }

    public String getTaskDefId() {
        return taskDefId;
    }

    public void setTaskDefId(String taskDefId) {
        this.taskDefId = taskDefId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Map<String, Object> getParticipants() {
        return participants;
    }

    public void setParticipants(Map<String, Object> participants) {
        this.participants = participants;
    }
}
