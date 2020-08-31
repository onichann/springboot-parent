package com.wt.activiti.flow;

/**
 * @author Administrator
 * @date 2020-07-06 下午 2:30
 * introduction
 */
public class FlowElement {
    private String id;
    private String name;
    private FlowElementType flowElementType;

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

    public FlowElementType getFlowElementType() {
        return flowElementType;
    }

    public void setFlowElementType(FlowElementType flowElementType) {
        this.flowElementType = flowElementType;
    }
}
