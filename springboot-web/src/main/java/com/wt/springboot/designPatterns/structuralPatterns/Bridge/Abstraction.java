package com.wt.springboot.designPatterns.structuralPatterns.Bridge;

public abstract class Abstraction {
    protected Implementor implementor;
    public Abstraction(Implementor implementor){
        this.implementor=implementor;
        System.out.println("抽象类有参构造方法");
    }

    public  Abstraction(){
        System.out.println("抽象类无参构造方法");
    }

    public void operation(){
        implementor.operationImpl();
    }

    public abstract  void toSomePlace();
}
