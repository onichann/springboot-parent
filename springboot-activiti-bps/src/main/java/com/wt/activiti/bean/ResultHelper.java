package com.wt.activiti.bean;

/**
 * @author Administrator
 * @date 2020-03-17 下午 3:35
 * description
 */
public class ResultHelper {

    public ResultHelper() {

    }
    public static <T> Result<T> buildResult(T data,String message,boolean success) {
        Result<T> result = new Result<>();
        result.setResult(data);
        result.setMessage(message);
        result.setSuccess(success);
        return result;
    }
}
