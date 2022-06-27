package com.wt.springboot.designPatterns.behavioralPatterns.RespChain;

/**
 * @author wutong
 * @date 2022/6/27 11:12
 * 说明:
 */
public class ConcreteHandler1 extends Handler {
    protected Level getHandlerLevel() {
        return new Level(1);
    }
    public Response response(Request request) {
        System.out.println("-----请求由处理器1进行处理-----");
        return null;
    }
}
