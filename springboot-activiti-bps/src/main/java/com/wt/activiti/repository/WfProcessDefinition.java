package com.wt.activiti.repository;

/**
 * @author Administrator
 * @date 2020-03-17 下午 4:30
 * description
 */
public class WfProcessDefinition {
    private String id;
    private String deploymentId;
    private String name;
    private String key;
    private int version;
    private String resourceName;
    private String diagramResourceName;
    private String description;

    long processDefID = -9223372036854775807L;
    String processDefName = null;
    String processChName = null;
//    String description = null;
    int currentState = -2147483647;
    String versionSign = null;
    String versionDesc = null;
    String currentFlag = null;
    String createTime = null;
    String updateTime = null;
    String operator = null;
    String processDefOwner = null;
    String packageID = null;
    String packageName = null;
    String hasActiveInstance = null;
    String catalogUUID;
    String longProcess;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDiagramResourceName() {
        return diagramResourceName;
    }

    public void setDiagramResourceName(String diagramResourceName) {
        this.diagramResourceName = diagramResourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getProcessDefID() {
        return processDefID;
    }

    public void setProcessDefID(long processDefID) {
        this.processDefID = processDefID;
    }

    public String getProcessDefName() {
        return processDefName;
    }

    public void setProcessDefName(String processDefName) {
        this.processDefName = processDefName;
    }

    public String getProcessChName() {
        return processChName;
    }

    public void setProcessChName(String processChName) {
        this.processChName = processChName;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public String getVersionSign() {
        return versionSign;
    }

    public void setVersionSign(String versionSign) {
        this.versionSign = versionSign;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getCurrentFlag() {
        return currentFlag;
    }

    public void setCurrentFlag(String currentFlag) {
        this.currentFlag = currentFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getProcessDefOwner() {
        return processDefOwner;
    }

    public void setProcessDefOwner(String processDefOwner) {
        this.processDefOwner = processDefOwner;
    }

    public String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getHasActiveInstance() {
        return hasActiveInstance;
    }

    public void setHasActiveInstance(String hasActiveInstance) {
        this.hasActiveInstance = hasActiveInstance;
    }

    public String getCatalogUUID() {
        return catalogUUID;
    }

    public void setCatalogUUID(String catalogUUID) {
        this.catalogUUID = catalogUUID;
    }

    public String getLongProcess() {
        return longProcess;
    }

    public void setLongProcess(String longProcess) {
        this.longProcess = longProcess;
    }
}
