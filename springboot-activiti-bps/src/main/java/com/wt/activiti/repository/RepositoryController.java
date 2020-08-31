package com.wt.activiti.repository;


import com.wt.activiti.bean.Result;
import com.wt.activiti.bean.ResultHelper;
import com.wt.activiti.pagination.IPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 * @author Administrator
 * @date 2020-03-17 下午 1:54
 * description
 */
@RestController
@RequestMapping("/repository")
public class RepositoryController {
    private static final Log logger = LogFactory.getLog(RepositoryController.class);

    @Autowired(required = false)
    private RepositoryService repositoryService;

    /**
     * 发布流程
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/deploy")
    public Result<Deployments> deployZip(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null) {
            throw new FileNotFoundException("流程zip文件未上传");
        }
        Deployments deploy = repositoryService.deploy(file);
        return ResultHelper.buildResult(deploy, "流程部署成功", true);
    }

    /**
     * 查询流程定义
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/definition/query")
    public Result<IPage<WfProcessDefinition>> getProcessDefinitionPageList(@RequestParam("page") int page,
                                                                           @RequestParam("rows") int rows) {
        IPage<WfProcessDefinition> processDefinitionPageList = repositoryService.getProcessDefinitionPageList(page, rows);
        return ResultHelper.buildResult(processDefinitionPageList, "流程定义查询成功", true);
    }

    /**
     *删除流程定义及流程定义相关的流程实例等
     * @param deploymentId
     */
    @GetMapping("/deployment/delete")
    public void deleteDeployment(@RequestParam("deploymentId") String deploymentId) {
        repositoryService.deleteDeployment(deploymentId);
    }

}
