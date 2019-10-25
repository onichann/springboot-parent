package com.wt.springboot.designPatterns.structuralPatterns.Bridge;

public class Test {
    public static void main(String[] args) {
        ConcreteImplementorA concreteImplementorA=new ConcreteImplementorA();
        ConcreteImplementorB concreteImplementorB=new ConcreteImplementorB();
        ConcreteAbstractionA concreteAbstractionA=new ConcreteAbstractionA(concreteImplementorA);
        ConcreteAbstractionB concreteAbstractionB=new ConcreteAbstractionB(concreteImplementorB);
        concreteAbstractionA.operation();
        concreteAbstractionA.toSomePlace();
        concreteAbstractionB.operation();
        concreteAbstractionB.toSomePlace();
    }
}
