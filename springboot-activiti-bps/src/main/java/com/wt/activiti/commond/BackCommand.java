package com.wt.activiti.commond;

import org.activiti.bpmn.model.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 流程回退命令
 */
public class BackCommand implements Command<String> {

	private Task task;

	public BackCommand(Task task) {
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
		for (SequenceFlow sequenceFlow : flow) {
			executionEntity.setCurrentFlowElement(sequenceFlow);
			context.getAgenda().planTakeOutgoingSequenceFlowsOperation(executionEntity, true);
		}
		return task.getId();
	}

	private List<FlowElement> getPreNode(Task task, CommandContext context) {
		HistoryService historyService = context.getProcessEngineConfiguration().getHistoryService();
		List<HistoricActivityInstance> items = historyService.createHistoricActivityInstanceQuery()
				.executionId(task.getExecutionId())
//				.activityType("userTask")
				.orderByHistoricActivityInstanceStartTime()
				.desc()
				.list();
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
}
