package com.wt.springboot.designPatterns.behavioralPatterns.Strategy;

public class test {
    public static void main(String[] args) {
        Context context;
        context=new Context(new ConcreteStrategy());
        context.execute();
        context=new Context(new ConcreteStrategy2());
        context.execute();
    }
}
