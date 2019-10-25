package com.wt.springboot.designPatterns.structuralPatterns.Bridge;

public class ConcreteAbstractionB extends Abstraction {
    public ConcreteAbstractionB(Implementor implementor){
        super(implementor);
    }
    @Override
    public void toSomePlace() {
        System.out.println("随便去哪吧");
    }
}
