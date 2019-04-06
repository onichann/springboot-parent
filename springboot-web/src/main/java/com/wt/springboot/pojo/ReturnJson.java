package com.wt.springboot.pojo;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Component("returnJson")
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReturnJson implements Serializable {
    private static final long serialVersionUID = -3049433887514970612L;
    private boolean success;
    private String  message;
    private Object  Data;
    private int code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @PostConstruct
    public void pre(){
        this.setMessage("初始化生成Message");
        System.out.println(JSON.toJSONString(this));
    }
}
