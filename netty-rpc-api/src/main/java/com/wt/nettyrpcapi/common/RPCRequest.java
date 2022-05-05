package com.wt.nettyrpcapi.common;

import lombok.Data;

/**
 * @author wutong
 * @date 2022/4/22 14:59
 * 说明:
 */
@Data
public class RPCRequest {

    private String requestId;

    private String className;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] parameters;
}
