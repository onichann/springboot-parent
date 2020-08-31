package com.wt.activiti.back;

/**
 * @author Administrator
 * @date 2020-03-23 下午 4:00
 * description
 */
public interface BackService {

    void backTask(String taskId);

    void hqBackTask(String taskId);
}
