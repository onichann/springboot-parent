package com.wt.activiti.helper;

import java.util.List;

/**
 * @author Administrator
 * @date 2020-07-07 上午 11:40
 * introduction
 */
public interface ProcessInstanceMeta {

    String getProcessInstanceId();

    List<String> getActiveActivitiesIds();
}
