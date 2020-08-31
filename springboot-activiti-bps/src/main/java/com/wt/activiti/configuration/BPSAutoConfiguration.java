package com.wt.activiti.configuration;

import com.eos.workflow.api.BPSServiceClientFactory;
import com.eos.workflow.api.IWFDefinitionQueryManager;
import com.gisinfo.activiti.repository.BPSWFDefinitionQueryManager;
import com.gisinfo.activiti.repository.RepositoryService;
import com.primeton.workflow.api.WFServiceException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @date 2020-06-12 下午 2:06
 * introduction
 */
@Configuration
@ConditionalOnClass(name="com.eos.workflow.api.BPSServiceClientFactory")
public class BPSAutoConfiguration {

    /**
     * 取得流程结构查询器
     *
     * @return 流程结构查询器
     * @throws WFServiceException
     */
    @Bean
    public IWFDefinitionQueryManager getWFDefinitionQueryManager() throws WFServiceException {
        return  BPSServiceClientFactory.getDefaultClient().getDefinitionQueryManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public RepositoryService repositoryService() {
        return new BPSWFDefinitionQueryManager();
    }

}
