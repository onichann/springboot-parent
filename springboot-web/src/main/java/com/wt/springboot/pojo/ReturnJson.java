package com.wt.springboot.pojo;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Accessors(chain = true)
@Getter
@Setter
public class ReturnJson implements Serializable {
    private static final long serialVersionUID = -3049433887514970612L;
    private boolean success;
    private String  message;
    private Object  data;
    private int code;

    @PostConstruct
    public void pre(){
        this.setMessage("初始化生成Message");
        System.out.println(JSON.toJSONString(this));
    }
}
