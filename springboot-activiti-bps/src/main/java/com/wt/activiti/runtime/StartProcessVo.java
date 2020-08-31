package com.wt.activiti.runtime;

import java.util.Map;

/**
 * @author Administrator
 * @date 2020-03-20 下午 6:40
 * description
 */
public class StartProcessVo {
    private String createUserId;
    private String processDefinitionKey;
    private String businessKey;
    private Map<String, Object> variables;

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
