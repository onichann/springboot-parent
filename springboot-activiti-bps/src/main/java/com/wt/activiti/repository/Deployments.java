package com.wt.activiti.repository;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * @date 2020-03-17 下午 2:33
 * description
 */
public class Deployments implements Serializable {
    private static final long serialVersionUID = -1328966038859261710L;
    private String id;
    private String name;
    private Date deploymentTime;
    private WfProcessDefinition wfProcessDefinition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(Date deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    public WfProcessDefinition getWfProcessDefinition() {
        return wfProcessDefinition;
    }

    public void setWfProcessDefinition(WfProcessDefinition wfProcessDefinition) {
        this.wfProcessDefinition = wfProcessDefinition;
    }
}
