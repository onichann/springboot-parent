package com.wt.activiti.flow;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * @date 2020-03-19 下午 6:07
 * description
 */
public interface FlowService {
    void showFlowChart(HttpServletResponse response, String processInstanceId);

    ProcessModel getPorcessModel(String processDefinitionId);
}
