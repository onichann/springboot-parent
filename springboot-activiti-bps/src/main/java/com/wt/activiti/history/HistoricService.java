package com.wt.activiti.history;

import java.util.List;

/**
 * @author Administrator
 * @date 2020-03-18 下午 5:26
 * description
 */
public interface HistoricService {
    List<HistoricActivityInstance> queryHistoricActivityInstance(String processInstanceId);
}
