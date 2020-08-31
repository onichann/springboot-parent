package com.wt.activiti.flow;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * @date 2020-03-19 下午 6:04
 * description
 */
@Controller
@RequestMapping("/flow")
public class FlowChartContorller {

    @Autowired
    private FlowService flowService;

    /**
     * 使用方式:
     * <embed src="/flow/display?processInstanceId=5070fd58-f859-11e8-a359-484d7ec5762d" style="display:block;width:1000px;height:450px" />
     * @param processInstanceId
     * @param response
     */
    @GetMapping("/display")
    public void image(@Param("processInstanceId") String processInstanceId, HttpServletResponse response) {
        flowService.showFlowChart(response, processInstanceId);
    }
}
