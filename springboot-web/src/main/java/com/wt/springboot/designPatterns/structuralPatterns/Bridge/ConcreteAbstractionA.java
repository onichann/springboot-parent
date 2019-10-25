package com.wt.springboot.designPatterns.structuralPatterns.Bridge;

public class ConcreteAbstractionA extends Abstraction {

    public ConcreteAbstractionA(Implementor implementor) {
        super(implementor);
    }

    @Override
    public void toSomePlace() {
        System.out.println("去西天玩玩");
    }

    public void otherOperation() {
        // 实现一定的功能，可能会使用具体实现部分的实现方法,
        // 但是本方法更大的可能是使用 Abstraction 中定义的方法，
        // 通过组合使用 Abstraction 中定义的方法来完成更多的功能。
    }
}
