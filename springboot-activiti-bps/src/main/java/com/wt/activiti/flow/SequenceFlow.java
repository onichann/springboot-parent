package com.wt.activiti.flow;

/**
 * @author Administrator
 * @date 2020-07-06 下午 2:30
 * introduction
 */
public class SequenceFlow extends FlowElement {
    private FlowElement sourceFlowElement;
    private FlowElement targetFlowElement;

    public FlowElement getSourceFlowElement() {
        return sourceFlowElement;
    }

    public void setSourceFlowElement(FlowElement sourceFlowElement) {
        this.sourceFlowElement = sourceFlowElement;
    }

    public FlowElement getTargetFlowElement() {
        return targetFlowElement;
    }

    public void setTargetFlowElement(FlowElement targetFlowElement) {
        this.targetFlowElement = targetFlowElement;
    }
}
