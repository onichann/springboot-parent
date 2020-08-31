package com.wt.activiti.repository;

import com.eos.workflow.api.IWFDefinitionQueryManager;
import com.eos.workflow.data.WFActivityDefine;
import com.eos.workflow.data.WFProcessDefine;
import com.primeton.workflow.api.WFServiceException;
import com.wt.activiti.pagination.IPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2020-06-12 下午 5:16
 * introduction
 */
public class BPSWFDefinitionQueryManager implements RepositoryService{

    private static final Log logger = LogFactory.getLog(BPSWFDefinitionQueryManager.class);

    @Autowired
    private IWFDefinitionQueryManager definitionQueryManager;

    @Override
    public Deployments deploy(MultipartFile file) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IPage<WfProcessDefinition> getProcessDefinitionPageList(int page, int rows) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteDeployment(String deploymentId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<WfProcessDefinition> getProcessDefinitionList() {
        List<WfProcessDefinition> wfProcessDefinitions = new ArrayList<>();
        try {
            List<WFProcessDefine> wfProcessDefines =definitionQueryManager.queryPublishedProcesses(null);
            wfProcessDefinitions = convertToWfProcessDefinitions(wfProcessDefines);
        } catch (WFServiceException e) {
            logger.error(e);
        }
        return wfProcessDefinitions;
    }

    private List<WfProcessDefinition> convertToWfProcessDefinitions(List<WFProcessDefine> processDefinitions) {
        List<WfProcessDefinition> wfProcessDefinitions = new ArrayList<>();
        processDefinitions.forEach(d->{
            WfProcessDefinition wfProcessDefinition = new WfProcessDefinition();
            wfProcessDefinition.setId(String.valueOf(d.getProcessDefID()));
            wfProcessDefinition.setName(d.getProcessDefName());
            BeanUtils.copyProperties(d, wfProcessDefinition);
            wfProcessDefinitions.add(wfProcessDefinition);
        });
        return wfProcessDefinitions;
    }

    @Override
    public List<WfActivityDefine> queryActivitiesOfProcess(String processDefID) {
        List<WfActivityDefine> wfActivityDefineList=new ArrayList<>();
        try {
            List<WFActivityDefine> wfActivityDefines = definitionQueryManager.queryActivitiesOfProcess(Long.parseLong(processDefID));
            wfActivityDefines.stream().filter(ad->(!"start".equalsIgnoreCase(ad.getType()))&&(!"finish".equalsIgnoreCase(ad.getType()))).forEach(d->{
                WfActivityDefine wfActivityDefine = new WfActivityDefine();
                wfActivityDefine.setId(d.getId());
                wfActivityDefine.setName(d.getName());
                wfActivityDefineList.add(wfActivityDefine);
            });
        } catch (WFServiceException e) {
            logger.error(e);
        }
        return wfActivityDefineList;
    }
}
