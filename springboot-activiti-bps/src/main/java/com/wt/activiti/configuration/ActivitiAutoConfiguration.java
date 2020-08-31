package com.wt.activiti.configuration;


import com.wt.activiti.back.BackService;
import com.wt.activiti.back.BackServiceImpl;
import com.wt.activiti.flow.FlowService;
import com.wt.activiti.flow.FlowServiceImpl;
import com.wt.activiti.helper.IProcessHelper;
import com.wt.activiti.helper.ProcessHelper;
import com.wt.activiti.history.HistoricService;
import com.wt.activiti.history.SimpleHistoricService;
import com.wt.activiti.repository.RepositoryService;
import com.wt.activiti.repository.RepositoryServiceImpl;
import com.wt.activiti.runtime.RuntimeService;
import com.wt.activiti.runtime.RuntimeServiceImpl;
import com.wt.activiti.task.TaskService;
import com.wt.activiti.task.TaskServiceImpl;
import org.activiti.engine.ProcessEngine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @date 2020-03-17 下午 2:55
 * description
 */
@Configuration
@ConditionalOnBean({ActivitiMarkerConfiguration.Marker.class})
@ConditionalOnClass(ProcessEngine.class)
public class ActivitiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RepositoryService repositoryService() {
        return new RepositoryServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public RuntimeService runtimeService(){
        return new RuntimeServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public HistoricService historicService() {
        return new SimpleHistoricService();
    }

    @Bean
    @ConditionalOnMissingBean
    public TaskService taskService() {
        return new TaskServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public BackService backService() {
        return new BackServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public FlowService flowService() {
        return new FlowServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public IProcessHelper processHelper(){
        return new ProcessHelper();
    }
}
