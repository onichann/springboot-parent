package com.wt.springboot.designPatterns.behavioralPatterns.RespChain;

/**
 * @author wutong
 * @date 2022/6/27 11:14
 * 说明:
 */
public class Client {
    public static void main(String[] args){
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        Handler handler3 = new ConcreteHandler3();

        handler1.setNextHandler(handler2);
        handler2.setNextHandler(handler3);

        Response response = handler1.handleRequest(new Request(new Level(4)));
    }
}
