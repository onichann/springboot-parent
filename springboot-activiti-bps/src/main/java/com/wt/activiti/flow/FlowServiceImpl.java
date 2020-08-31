package com.wt.activiti.flow;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2020-03-20 上午 9:47
 * description
 */
public class FlowServiceImpl implements FlowService{

    private static final Log logger = LogFactory.getLog(com.wt.activiti.flow.FlowServiceImpl.class);

    @Autowired
    private org.activiti.engine.HistoryService historicService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessDiagramGenerator processDiagramGenerator;

    @Override
    public void showFlowChart(HttpServletResponse response, String processInstanceId) {
        /*
         *  获取流程实例
         */
        HistoricProcessInstance processInstance = historicService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if(processInstance == null) {
            logger.error(String.format("流程实例ID:%s没查询到流程实例！", processInstanceId));
            return;
        }
        // 根据流程对象获取流程对象模型
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        /*
         *  查看已执行的节点集合
         *  获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
         */
        // 构造历史流程查询
        HistoricActivityInstanceQuery historyInstanceQuery = historicService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId);
        // 查询历史节点
        List<HistoricActivityInstance> historicActivityInstanceList = dleteht(historyInstanceQuery.orderByHistoricActivityInstanceStartTime().asc().list());
        if(historicActivityInstanceList.isEmpty()) {
            logger.info(String.format("流程实例ID:%s没有历史节点信息！", processInstanceId));
            outputImg(response, bpmnModel, null, null);
            return;
        }
        // 已执行的节点ID集合(将historicActivityInstanceList中元素的activityId字段取出封装到executedActivityIdList)
        List<String> executedActivityIdList = historicActivityInstanceList.stream().map(HistoricActivityInstance::getActivityId).collect(Collectors.toList());
        /*
         *  获取流程走过的线
         */
        // 获取流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
        List<String> flowIds = ActivitiUtils.getHighLightedFlows(bpmnModel, processDefinition, historicActivityInstanceList);

        /*
         * 输出图像，并设置高亮
         */
        outputImg(response, bpmnModel, flowIds, executedActivityIdList);
    }

    //在集合历史任务集合里删除已回退过的任务
    private List<HistoricActivityInstance> dleteht(List<HistoricActivityInstance> historicActivityInstanceList) {
        Iterator<HistoricActivityInstance> iterator = historicActivityInstanceList.iterator();
        HistoricActivityInstance startEvent = null;
        while(iterator.hasNext()){
            HistoricActivityInstance next = iterator.next();
            if(next.getTaskId()!=null){
                HistoricTaskInstance historicTaskInstance = historicService.createHistoricTaskInstanceQuery().taskId(next.getTaskId()).singleResult();
                if(StringUtils.isEmpty(historicTaskInstance)){
                    iterator.remove();
                    continue;
                }
            }
            //默认删除default用户的流程图上图标的颜色。实际流程是处于运行状态
            if(!StringUtils.isEmpty(next.getAssignee()) && "default".equals(next.getAssignee())){
                iterator.remove();
            }
        }
        if(startEvent!=null){
            historicActivityInstanceList.add(startEvent);
        }
        return historicActivityInstanceList;
    }

    /**
     * 获取流程图元信息
     * @param processDefinitionId 流程定义id
     * @return 流程图元信息
     */
    @Override
    public ProcessModel getPorcessModel(String processDefinitionId) {
        if(ObjectUtils.isEmpty(processDefinitionId)) {
            logger.error(String.format("流程定义ID:%s不能为空！", processDefinitionId));
            return null;
        }
        BpmnModel bpmnModel=repositoryService.getBpmnModel(processDefinitionId);
        if(bpmnModel==null) {
            logger.error(String.format("流程定义ID:%s没查询到流程定义！", processDefinitionId));
            return null;
        }
        ProcessModel processModel = new ProcessModel();
        //流程图节点坐标信息
        getLocationMap(bpmnModel, processModel);
        //流程图连线坐标信息
        getFlowLocationMap(bpmnModel, processModel);
        //流程图中节点及连线信息
        getFlowElementMap(bpmnModel, processModel);
        return processModel;
    }

    private void getFlowElementMap(BpmnModel bpmnModel, ProcessModel processModel) {
        Map<String, FlowElement> flowElementMap = bpmnModel.getMainProcess().getFlowElementMap();
        processModel.setFlowElementMap(flowElementMap.entrySet().stream().flatMap(entry->{
            FlowElement originFlowElement = entry.getValue();
            com.wt.activiti.flow.FlowElement flowElement = transformFlowElement(originFlowElement);
            if (flowElement.getFlowElementType() == FlowElementType.SequenceFlow) {
                SequenceFlow sequenceFlow=new SequenceFlow();
                BeanUtils.copyProperties(flowElement,sequenceFlow);
                sequenceFlow.setSourceFlowElement(transformFlowElement(((org.activiti.bpmn.model.SequenceFlow)originFlowElement).getSourceFlowElement()));
                sequenceFlow.setTargetFlowElement(transformFlowElement(((org.activiti.bpmn.model.SequenceFlow)originFlowElement).getTargetFlowElement()));
                flowElement = sequenceFlow;
            }
            Map<String, com.wt.activiti.flow.FlowElement> temp = new HashMap<>();
            temp.put(entry.getKey(), flowElement);
            return temp.entrySet().stream();
        }).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1, e2)->e1, LinkedHashMap::new)));
    }

    private com.wt.activiti.flow.FlowElement transformFlowElement(FlowElement originFlowElement) {
        FlowElementType flowElementType = new FlowElementHelper().detectType(originFlowElement);
        com.wt.activiti.flow.FlowElement flowElement = new com.wt.activiti.flow.FlowElement();
        flowElement.setId(originFlowElement.getId());
        flowElement.setName(originFlowElement.getName());
        flowElement.setFlowElementType(flowElementType);
        return flowElement;
    }


    private void getFlowLocationMap(BpmnModel bpmnModel, ProcessModel processModel) {
        Map<String, List<GraphicInfo>> flowLocationMap = bpmnModel.getFlowLocationMap();
        processModel.setFlowLocationMap(flowLocationMap.entrySet().stream().map(entry->{
            List<GraphicInfo> originGraphicInfoList = entry.getValue();
            List<com.wt.activiti.flow.GraphicInfo> graphicInfoList = originGraphicInfoList.stream().map(i -> {
                com.wt.activiti.flow.GraphicInfo graphicInfo = new com.wt.activiti.flow.GraphicInfo();
                graphicInfo.setX(i.getX());
                graphicInfo.setY(i.getY());
                graphicInfo.setWidth(i.getWidth());
                graphicInfo.setHeight(i.getHeight());
                return graphicInfo;
            }).collect(Collectors.toList());
            Map<String, List<com.wt.activiti.flow.GraphicInfo>> temp=new HashMap<>();
            temp.put(entry.getKey(), graphicInfoList);
            return temp;
        }).reduce(new LinkedHashMap<>(),(m1, m2)->{m1.putAll(m2);return m1;}));
    }

    private void getLocationMap(BpmnModel bpmnModel, ProcessModel processModel) {
        Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
        processModel.setLocationMap(locationMap.entrySet().stream().map(entry -> {
            com.wt.activiti.flow.GraphicInfo graphicInfo = new com.wt.activiti.flow.GraphicInfo();
            GraphicInfo originGraphicInfo = entry.getValue();
            graphicInfo.setX(originGraphicInfo.getX());
            graphicInfo.setY(originGraphicInfo.getY());
            graphicInfo.setWidth(originGraphicInfo.getWidth());
            graphicInfo.setHeight(originGraphicInfo.getHeight());
            Map<String, com.wt.activiti.flow.GraphicInfo> temp = new HashMap<>();
            temp.put(entry.getKey(), graphicInfo);
            return temp;
        }).reduce(new LinkedHashMap<>(), (m1, m2) -> { m1.putAll(m2); return m1; }));
    }



    /**
     * <p>输出图像</p>
     * @param response 响应实体
     * @param bpmnModel 图像对象
     * @param flowIds 已执行的线集合
     * @param executedActivityIdList void 已执行的节点ID集合
     */
    private void outputImg(HttpServletResponse response, BpmnModel bpmnModel, List<String> flowIds, List<String> executedActivityIdList) {
        InputStream imageStream = null;
        OutputStream outputStream = null;
        response.setContentType("image/svg+xml;charset=UTF-8");
        response.addHeader("Accept-Ranges", "bytes");
        try {
            imageStream = processDiagramGenerator.generateDiagram(bpmnModel, executedActivityIdList, flowIds, "宋体", "微软雅黑", "黑体", true, "png");
            outputStream = response.getOutputStream();
            IOUtils.write(IOUtils.toByteArray(imageStream), outputStream);
        } catch (Exception e) {
            logger.error("流程图输出异常！", e);
        } finally { // 流关闭
            IoUtil.closeSilently(imageStream);
            IoUtil.closeSilently(outputStream);
        }
    }

     static class FlowElementHelper{

        public FlowElementType detectType(@NotNull FlowElement flowElement) {
            FlowElementType flowElementType = null;
            String simpleName = flowElement.getClass().getSimpleName();
            switch (simpleName) {
                case "StartEvent":
                case "UserTask":
                case "SequenceFlow":
                case "ParallelGateway":
                case "EndEvent":
                case "ExclusiveGateway":
                    flowElementType = FlowElementType.valueOfName(simpleName);
                    break;
                default:
            }
            return flowElementType;
        }
    }
}
