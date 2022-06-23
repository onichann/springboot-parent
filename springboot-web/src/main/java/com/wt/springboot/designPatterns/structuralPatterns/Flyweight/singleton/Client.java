package com.wt.springboot.designPatterns.structuralPatterns.Flyweight.singleton;

/**
 * @author wutong
 * @date 2022/6/23 14:13
 * 说明:
 */
public class Client {
    public static void main(String[] args) {
        FlyweightFactory flyweightFactory = new FlyweightFactory();
        Flyweight flyweight = flyweightFactory.factory("a");
        flyweight.operation("FirstCall");
        flyweight = flyweightFactory.factory("b");
        flyweight.operation("secend call");
        flyweight = flyweightFactory.factory("a");
        flyweight.operation("thrid call");

    }
}
