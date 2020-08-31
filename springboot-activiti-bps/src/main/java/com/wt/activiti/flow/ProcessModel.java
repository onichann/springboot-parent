package com.wt.activiti.flow;



import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2020-07-06 下午 1:50
 * introduction
 */
public class ProcessModel {
    //流程图节点坐标信息
    private Map<String, GraphicInfo> locationMap = new LinkedHashMap<>();
    //流程图连线坐标信息
    private Map<String, List<GraphicInfo>> flowLocationMap = new LinkedHashMap<>();
    //流程图中节点及连线信息
    private Map<String, FlowElement> flowElementMap = new LinkedHashMap<>();

    public Map<String, GraphicInfo> getLocationMap() {
        return locationMap;
    }

    public void setLocationMap(Map<String, GraphicInfo> locationMap) {
        this.locationMap = locationMap;
    }

    public Map<String, List<GraphicInfo>> getFlowLocationMap() {
        return flowLocationMap;
    }

    public void setFlowLocationMap(Map<String, List<GraphicInfo>> flowLocationMap) {
        this.flowLocationMap = flowLocationMap;
    }

    public Map<String, FlowElement> getFlowElementMap() {
        return flowElementMap;
    }

    public void setFlowElementMap(Map<String, FlowElement> flowElementMap) {
        this.flowElementMap = flowElementMap;
    }
}
