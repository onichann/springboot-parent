package com.wt.springboot.designPatterns.structuralPatterns.Decorator;

public class OldPerson implements Person {
    @Override
    public void eat() {
        System.out.println("吃饭");
    }
}
