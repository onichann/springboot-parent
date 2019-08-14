package com.wt.springboot.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Administrator
 * @date 2019-08-14 下午 3:30
 * PROJECT_NAME sand-demo
 */
@Accessors(chain = true)
@Getter
@Setter
public class Result {
    private Boolean success;
    private Object data;
    private String message;
}
