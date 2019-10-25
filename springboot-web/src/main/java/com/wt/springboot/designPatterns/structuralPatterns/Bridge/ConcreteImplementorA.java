package com.wt.springboot.designPatterns.structuralPatterns.Bridge;

public class ConcreteImplementorA implements Implementor {
    @Override
    public void operationImpl() {
        System.out.println("老子从东土大唐而来");
    }
}
