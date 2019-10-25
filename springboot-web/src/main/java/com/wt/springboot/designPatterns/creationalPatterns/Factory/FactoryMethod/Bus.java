package com.wt.springboot.designPatterns.creationalPatterns.Factory.FactoryMethod;

public class Bus implements Car {
    @Override
    public void gotoWork() {
        System.out.println("use Bus to gotoWork");
    }
}
