package com.wt.activiti.repository;


import com.wt.activiti.pagination.IPage;
import com.wt.activiti.pagination.Page;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

/**
 * @author Administrator
 * @date 2020-03-17 下午 2:34
 * description
 */
public class RepositoryServiceImpl implements RepositoryService{

    private static final Log logger = LogFactory.getLog(RepositoryServiceImpl.class);

    @Autowired
    org.activiti.engine.RepositoryService repositoryService;

    @Override
    public Deployments deploy(MultipartFile file) {
        Deployments deployments = null;
        try {
            ZipInputStream zipInputStream = new ZipInputStream(file.getResource().getInputStream(), Charset.forName("UTF-8"));
            Deployment deployment = repositoryService// 与流程定义和部署对象相关的Service
                .createDeployment()// 创建一个部署对象
                .name(file.getOriginalFilename())// 添加部署名称
                .addZipInputStream(zipInputStream)// 完成zip文件的部署
                .deploy();// 完成部署
            deployments=loadDeployments(deployment);
        } catch (IOException e) {
            logger.error(e);
        }
        return deployments;
    }

    @Override
    public IPage<WfProcessDefinition> getProcessDefinitionPageList(int page, int rows) {
        long count = repositoryService.createProcessDefinitionQuery().count();
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionName().orderByProcessDefinitionVersion().desc().listPage(page - 1, rows);
        List<WfProcessDefinition> wfProcessDefinitions = convertToWfProcessDefinition(processDefinitions);
        IPage<WfProcessDefinition> iPage = new Page<>();
        iPage.setCurrent(page);
        iPage.setSize(rows);
        iPage.setTotal(count);
        iPage.setRecords(wfProcessDefinitions);
        return iPage;
    }

    @Override
    public void deleteDeployment(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId,true);
    }

    @Override
    public List<WfProcessDefinition> getProcessDefinitionList() {
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionName().desc().list();
        return convertToWfProcessDefinition(processDefinitions);
    }

    private Deployments loadDeployments(Deployment deployment) {
        Deployments deployments = new Deployments();
        deployments.setId(deployment.getId());
        deployments.setName(deployment.getName());
        deployments.setDeploymentTime(deployment.getDeploymentTime());
        List<ProcessDefinition> deployedArtifacts = ((DeploymentEntityImpl) deployment).getDeployedArtifacts(ProcessDefinition.class);
        if (deployedArtifacts.size() > 0) {
            ProcessDefinition processDefinition = deployedArtifacts.get(0);
            WfProcessDefinition wfProcessDefinition = new WfProcessDefinition();
            BeanUtils.copyProperties(processDefinition, wfProcessDefinition);
            deployments.setWfProcessDefinition(wfProcessDefinition);
        }
        return deployments;
    }

    private List<WfProcessDefinition> convertToWfProcessDefinition(List<ProcessDefinition> processDefinitions) {
        List<WfProcessDefinition> wfProcessDefinitions = new ArrayList<>();
        processDefinitions.forEach(d->{
            WfProcessDefinition wfProcessDefinition = new WfProcessDefinition();
            BeanUtils.copyProperties(d, wfProcessDefinition);
            wfProcessDefinitions.add(wfProcessDefinition);
        });
        return wfProcessDefinitions;
    }

    @Override
    public List<WfActivityDefine> queryActivitiesOfProcess(String processDefID) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefID);
        Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
        List<UserTask> userTasks = flowElements.stream().filter(UserTask.class::isInstance).map(UserTask.class::cast).sorted(Comparator.comparingInt(BaseElement::getXmlRowNumber)).collect(Collectors.toList());
        return userTasks.stream().map(userTask -> {
                    WfActivityDefine wfActivityDefine = new WfActivityDefine();
                    wfActivityDefine.setId(userTask.getId());
                    wfActivityDefine.setName(userTask.getName());
                    return wfActivityDefine;
                }).collect(Collectors.toList());
    }
}
