package com.wt.activiti.runtime;

import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author Administrator
 * @date 2020-03-18 下午 2:28
 * description
 */
public class RuntimeServiceImpl implements RuntimeService {

    @Autowired
    private org.activiti.engine.RuntimeService runtimeService;


    @Override
    public String startProcessInstanceByKey(String createUserId,String processDefinitionKey, String businessKey, Map<String, Object> variables) {
        Authentication.setAuthenticatedUserId(createUserId);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
        return processInstance.getProcessInstanceId();
    }

    @Override
    public void deleteProcessInstance(String processInstanceId, String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }

}
