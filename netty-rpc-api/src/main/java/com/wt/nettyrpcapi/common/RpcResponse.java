package com.wt.nettyrpcapi.common;

import lombok.Data;

/**
 * @author wutong
 * @date 2022/4/22 15:02
 * 说明:
 */
@Data
public class RpcResponse {

    private String requestId;

    private String error;

    private Object result;
}
