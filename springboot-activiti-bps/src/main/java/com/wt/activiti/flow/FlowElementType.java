package com.wt.activiti.flow;

import java.util.Objects;

/**
 * @author Administrator
 * @date 2020-07-06 下午 2:48
 * introduction
 */
public enum FlowElementType {
    StartEvent("StartEvent"),//开始事件
    UserTask("UserTask"),//用户任务
    SequenceFlow("SequenceFlow"),//流转线条
    ParallelGateway("ParallelGateway"),//并行网关
    EndEvent("EndEvent"),//结束事件
    ExclusiveGateway("ExclusiveGateway")//排他网关
    ;

    private String name;

    FlowElementType(String name) {
        this.name = name;
    }

    public static FlowElementType valueOfName(String name) {
        for (FlowElementType obj : FlowElementType.values()) {
            if (Objects.equals(obj.name, name)) {
                return obj;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
