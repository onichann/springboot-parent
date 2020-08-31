package com.wt.activiti.repository;

import java.util.List;

/**
 * @author Administrator
 * @date 2020-06-11 下午 3:19
 * introduction
 */
public interface DefinitionQueryService {
    List<WfActivityDefine> queryActivitiesOfProcess(String processDefID);
}
