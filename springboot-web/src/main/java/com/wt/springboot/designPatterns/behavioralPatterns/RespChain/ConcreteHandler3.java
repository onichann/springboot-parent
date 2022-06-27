package com.wt.springboot.designPatterns.behavioralPatterns.RespChain;

/**
 * @author wutong
 * @date 2022/6/27 11:13
 * 说明:
 */
public class ConcreteHandler3 extends Handler {

    protected Level getHandlerLevel() {
        return new Level(5);
    }
    public Response response(Request request) {
        System.out.println("-----请求由处理器3进行处理-----");
        return null;
    }
}
