package com.wt.springboot.pojo;

/**
 * @author Administrator
 * @date 2019-08-13 下午 3:19
 * PROJECT_NAME sand-demo
 */
public class SealResponse {

    /**
     * result : false
     * message : APP-0006:网络服务异常
     * type : String
     * data : failure to invoke CA seal api,retMsg:系统内部错误PdfReader not opened with owner password
     */

    private Boolean result;
    private String message;
    private String type;
    private String data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
