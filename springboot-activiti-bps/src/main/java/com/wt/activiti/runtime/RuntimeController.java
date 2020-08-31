package com.wt.activiti.runtime;


import com.wt.activiti.bean.Result;
import com.wt.activiti.bean.ResultHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @date 2020-03-18 上午 11:44
 * description
 */
@RestController
@RequestMapping("/runtime")
public class RuntimeController {
    private static final Log logger = LogFactory.getLog(RuntimeController.class);

    @Autowired(required = false)
    private RuntimeService runtimeService;

    @PostMapping("/processInstance/start")
    public Result<String> startProcess(@RequestBody StartProcessVo startProcessVo) {
        String processInstanceId = runtimeService.startProcessInstanceByKey(startProcessVo.getCreateUserId(),startProcessVo.getProcessDefinitionKey(),
                startProcessVo.getBusinessKey(), startProcessVo.getVariables());
        return ResultHelper.buildResult(processInstanceId, "流程启动成功", true);
    }

    @GetMapping("/processInstance/delete")
    public void deleteProcess(@RequestParam("processInstanceId") String processInstanceId,
                              @RequestParam(name = "deleteReason",required = false)String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId,deleteReason==null?"流程实例已删除":deleteReason);
    }

}
