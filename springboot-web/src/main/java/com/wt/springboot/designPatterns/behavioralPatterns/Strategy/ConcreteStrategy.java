package com.wt.springboot.designPatterns.behavioralPatterns.Strategy;

public class ConcreteStrategy implements Strategy {
    @Override
    public void doSomething() {
        System.out.println("执行策略1");
    }
}
