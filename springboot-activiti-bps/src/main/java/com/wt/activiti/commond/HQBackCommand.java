package com.wt.activiti.commond;

import cn.hutool.core.util.IdUtil;
import org.activiti.bpmn.model.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;
import org.activiti.engine.task.Task;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 流程回退命令
 */
public class HQBackCommand implements Command<String> {

	private Task task;

	public HQBackCommand(Task task) {
		this.task = task;
	}

	@Override
	public String execute(CommandContext context) {

		List<FlowElement> element = this.getPreNode(this.task, context);
		if (element == null) {
			throw new RuntimeException("该节点不能进行退回");
		}
		List<SequenceFlow> flow = this.findAcessSequenceFlow(element);
		ExecutionEntity executionEntity = context.getExecutionEntityManager().findById(task.getExecutionId());
		String originId = executionEntity.getId();
		List<ExecutionEntity> executionEntities=new ArrayList<>();
		for (int i = 0; i < flow.size(); i++) {
			if (i == 0) {
				executionEntities.add(executionEntity);
			}else{
				ExecutionEntity copyExecutionEntity = copyExecutionEntity(context, (ExecutionEntityImpl) executionEntity);
				executionEntities.add(copyExecutionEntity);
				executionEntity.setId(copyExecutionEntity.getId());
				context.getExecutionEntityManager().insert(copyExecutionEntity);
			}
		}
		executionEntity.setId(originId);

		for (int i = 0; i < flow.size(); i++) {
			executionEntities.get(i).setCurrentFlowElement(flow.get(i));
			context.getAgenda().planTakeOutgoingSequenceFlowsOperation(executionEntities.get(i), true);
//			if (i == 0) {
//				executionEntity.setCurrentFlowElement(flow.get(0));
//				context.getAgenda().planTakeOutgoingSequenceFlowsOperation(executionEntity, true);
//			}else{
////				ExecutionEntityImpl executionEntityClone = (ExecutionEntityImpl) SerializationUtils.clone((VariableScopeImpl)executionEntity);
////				executionEntity.setId(IdUtil.simpleUUID());
//////				executionEntity.setCurrentFlowElement(flow.get(i));
//////				this.back(context, (ExecutionEntityImpl) executionEntity);
//
//				executionEntity.setId(IdUtil.simpleUUID());
//				executionEntity.setCurrentFlowElement(flow.get(0));
//				context.getAgenda().planTakeOutgoingSequenceFlowsOperation(executionEntity, true);
//			}
		}
		return task.getId();
	}

	private ExecutionEntity copyExecutionEntity(CommandContext context,ExecutionEntityImpl executionEntity) {
		ExecutionEntityImpl copy = (ExecutionEntityImpl) context.getExecutionEntityManager().create();
		copy.setId(IdUtil.simpleUUID());
		copy.setConcurrent(executionEntity.isConcurrent());
		copy.setEnded(executionEntity.isEnded());
		copy.setEventScope(executionEntity.isEventScope());
		copy.setMultiInstanceRoot(executionEntity.isMultiInstanceRoot());
		copy.setCountEnabled(executionEntity.isCountEnabled());
		copy.setSuspensionState(executionEntity.getSuspensionState());
		copy.setStartTime(executionEntity.getStartTime());
		copy.setProcessDefinitionId(executionEntity.getProcessDefinitionId());
		copy.setProcessInstanceId(executionEntity.getProcessInstanceId());
		copy.setParentId(executionEntity.getParentId());
		copy.setRootProcessInstanceId(executionEntity.getRootProcessInstanceId());
		copy.setRevision(executionEntity.getRevision());
		copy.setDeleted(executionEntity.isDeleted());
		copy.setScope(executionEntity.isScope());
	    return copy;


	}

	private void back(CommandContext context,ExecutionEntityImpl executionEntity) {
//		context.getHistoryManager().recordActivityEnd(executionEntity,
//				null);

		FlowElement currentFlowElement = getCurrentFlowElement(executionEntity);
		SequenceFlow sequenceFlow=(SequenceFlow)currentFlowElement;
		if (Context.getProcessEngineConfiguration() != null && Context.getProcessEngineConfiguration().getEventDispatcher().isEnabled()) {
			FlowElement sourceFlowElement = sequenceFlow.getSourceFlowElement();
			FlowElement targetFlowElement = sequenceFlow.getTargetFlowElement();
			Context.getProcessEngineConfiguration().getEventDispatcher().dispatchEvent(
					ActivitiEventBuilder.createSequenceFlowTakenEvent(
							(ExecutionEntity) executionEntity,
							ActivitiEventType.SEQUENCEFLOW_TAKEN,
							sequenceFlow.getId(),
							sourceFlowElement != null ? sourceFlowElement.getId() : null,
							sourceFlowElement != null ? (String) sourceFlowElement.getName() : null,
							sourceFlowElement != null ? sourceFlowElement.getClass().getName() : null,
							sourceFlowElement != null ? ((FlowNode) sourceFlowElement).getBehavior() : null,
							targetFlowElement != null ? targetFlowElement.getId() : null,
							targetFlowElement != null ? targetFlowElement.getName() : null,
							targetFlowElement != null ? targetFlowElement.getClass().getName() : null,
							targetFlowElement != null ? ((FlowNode) targetFlowElement).getBehavior() : null));
		}

		FlowElement targetFlowElement = sequenceFlow.getTargetFlowElement();
		executionEntity.setCurrentFlowElement(targetFlowElement);
		Context.getAgenda().planContinueProcessOperation(executionEntity);
	}

	protected FlowElement getCurrentFlowElement(final ExecutionEntity execution) {
		if (execution.getCurrentFlowElement() != null) {
			return execution.getCurrentFlowElement();
		} else if (execution.getCurrentActivityId() != null) {
			String processDefinitionId = execution.getProcessDefinitionId();
			org.activiti.bpmn.model.Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
			String activityId = execution.getCurrentActivityId();
			FlowElement currentFlowElement = process.getFlowElement(activityId, true);
			return currentFlowElement;
		}
		return null;
	}

	private List<FlowElement> getPreNode(Task task, CommandContext context) {
		HistoryService historyService = context.getProcessEngineConfiguration().getHistoryService();
		List<HistoricActivityInstance> items = this.deleteHtNode(historyService.createHistoricActivityInstanceQuery()
				.executionId(task.getExecutionId())
//				.activityType("userTask")
				.orderByHistoricActivityInstanceStartTime()
				.desc()
				.list(),historyService);
		if (items == null || items.size() == 0) {
			throw new RuntimeException("未找到上一节点");
		}
		String currentAct = task.getTaskDefinitionKey();
		String preAct = null;
		for (int i = 0; i < items.size(); ++i) {
			HistoricActivityInstance item = items.get(i);
			if (currentAct.equals(item.getActivityId())) {
				continue;
			}
			preAct = item.getActivityId();
			break;
		}
		if (preAct == null) {
			return null;
		}
		RepositoryService repositoryService = context.getProcessEngineConfiguration().getRepositoryService();
		org.activiti.bpmn.model.Process process = repositoryService.getBpmnModel(task.getProcessDefinitionId()).getMainProcess();
		FlowElement node = process.getFlowElement(preAct);
		if (node instanceof ParallelGateway) {
			List<FlowElement> lists=new ArrayList<>();
			List<SequenceFlow> incomingFlows = ((ParallelGateway) node).getIncomingFlows();
			for (SequenceFlow incomingFlow : incomingFlows) {
				lists.add(incomingFlow.getSourceFlowElement());
			}
			return lists;
		} else if (node instanceof UserTask) {
			return Collections.singletonList(node);
		}
		return null;
	}

	private List<SequenceFlow> findAcessSequenceFlow(List<FlowElement> node) {
		List<SequenceFlow> flows = new ArrayList<>();
		for (FlowElement flowElement : node) {
			List<SequenceFlow> incomingFlows = ((FlowNode) flowElement).getIncomingFlows();
			if (incomingFlows == null || incomingFlows.size() == 0) {
				throw new RuntimeException(flowElement.getName()+"上一节点找不到入口");
			}
			//找没有加条件的连线
			for (SequenceFlow flow : incomingFlows) {
				if (flow.getConditionExpression() == null) {
					flows.add(flow);
				}
			}
		}
		return flows;
	}

	//在集合历史任务集合里删除已回退过的任务
	private List<HistoricActivityInstance> deleteHtNode(List<HistoricActivityInstance> historicActivityInstanceList,HistoryService historyService) {
		Iterator<HistoricActivityInstance> iterator = historicActivityInstanceList.iterator();
		HistoricActivityInstance startEvent = null;
		while(iterator.hasNext()){
			HistoricActivityInstance next = iterator.next();
			//防止特殊情况  （启动流程后开始环节与第一环节的starttime相同）
			if(next.getActivityType().equals("startEvent")){
				startEvent = next;
				iterator.remove();
			}else{
				if(next.getTaskId()!=null){
					HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(next.getTaskId()).singleResult();
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
		}
		if(startEvent!=null){
			historicActivityInstanceList.add(startEvent);
		}
		return historicActivityInstanceList;
	}
}
