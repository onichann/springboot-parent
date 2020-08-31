package com.wt.activiti.runtime;

import java.util.Map;

/**
 * @author Administrator
 * @date 2020-03-18 上午 11:49
 * description
 */
public interface RuntimeService {

    String startProcessInstanceByKey(String createUserId,String processDefinitionKey, String businessKey, Map<String,Object> variables);

    void deleteProcessInstance(String processInstanceId, String deleteReason);
}
