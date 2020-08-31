package com.wt.activiti.helper;

import org.activiti.api.process.runtime.ProcessRuntime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator
 * @date 2020-07-07 下午 1:52
 * introduction
 */
public class ProcessHelper implements IProcessHelper {


    @Autowired
    private ProcessRuntime processRuntime;

    @Override
    public ProcessInstanceMeta getProcessInstanceMeta(String processInstanceId) {
        org.activiti.api.process.model.ProcessInstanceMeta processInstanceMeta = processRuntime.processInstanceMeta(processInstanceId);
        ProcessInstanceMeta meta = new ProcessInstanceMetaImpl();
        BeanUtils.copyProperties(processInstanceMeta,meta);
        return meta;
    }
}
