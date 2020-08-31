package com.wt.activiti.history;

import org.activiti.engine.HistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2020-03-18 下午 5:29
 * description
 */
public class SimpleHistoricService implements HistoricService{

    @Autowired
    private HistoryService historyService;

    @Override
    public List<HistoricActivityInstance> queryHistoricActivityInstance(String processInstanceId) {
        List<org.activiti.engine.history.HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
        List<HistoricActivityInstance> historicActivityInstanceList = new ArrayList<>();
        historicActivityInstances.forEach(instance->{
            HistoricActivityInstance historicActivityInstance = new HistoricActivityInstance();
            BeanUtils.copyProperties(instance,historicActivityInstance);
            historicActivityInstanceList.add(historicActivityInstance);
        });
        return historicActivityInstanceList;
    }
}
