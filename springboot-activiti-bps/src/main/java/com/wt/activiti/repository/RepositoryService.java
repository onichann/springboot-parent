package com.wt.activiti.repository;


import com.wt.activiti.pagination.IPage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Administrator
 * @date 2020-03-17 下午 2:19
 * description
 */
public interface RepositoryService extends DefinitionQueryService{

    Deployments deploy(MultipartFile file);

    IPage<WfProcessDefinition> getProcessDefinitionPageList(int page, int rows);

    void deleteDeployment(String deploymentId);

    List<WfProcessDefinition> getProcessDefinitionList();



}
