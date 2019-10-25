package com.wt.springboot.designPatterns.structuralPatterns.Bridge;

public class ConcreteImplementorB implements Implementor {
    @Override
    public void operationImpl() {
        System.out.println("俺从山洞来的");
    }
}
