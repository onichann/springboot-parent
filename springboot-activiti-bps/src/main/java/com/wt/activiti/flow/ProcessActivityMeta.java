package com.wt.activiti.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2020-07-09 下午 4:04
 * introduction
 */
public class ProcessActivityMeta {
    //正在办理
    private List<FlowElement> processing = new ArrayList<>();
    //已经办理
    private List<FlowElement> processed = new ArrayList<>();
    //已经办理有回退
    private List<FlowElement> processedWithReturn = new ArrayList<>();
    //未办理
    private List<FlowElement> notProcessed = new ArrayList<>();
    //任务终止，即该任务进行了回退操作
    private List<FlowElement> notProcessedAndReturned = new ArrayList<>();

    public List<FlowElement> getProcessing() {
        return processing;
    }

    public void setProcessing(FlowElement processing) {
        this.processing.add(processing);
    }

    public List<FlowElement> getProcessed() {
        return processed;
    }

    public void setProcessed(FlowElement processed) {
        this.processed.add(processed);
    }

    public List<FlowElement> getProcessedWithReturn() {
        return processedWithReturn;
    }

    public void setProcessedWithReturn(List<FlowElement> processedWithReturn) {
        this.processedWithReturn = processedWithReturn;
    }

    public List<FlowElement> getNotProcessed() {
        return notProcessed;
    }

    public void setNotProcessed(List<FlowElement> notProcessed) {
        this.notProcessed = notProcessed;
    }

    public List<FlowElement> getNotProcessedAndReturned() {
        return notProcessedAndReturned;
    }

    public void setNotProcessedAndReturned(List<FlowElement> notProcessedAndReturned) {
        this.notProcessedAndReturned = notProcessedAndReturned;
    }
}
