package com.wt.springboot.designPatterns.behavioralPatterns.Observer;

public class ConcreteObserver2 implements  Observer{
    @Override
    public void update() {
        System.out.println("观察者2收到信息，进行处理。");
    }
}
