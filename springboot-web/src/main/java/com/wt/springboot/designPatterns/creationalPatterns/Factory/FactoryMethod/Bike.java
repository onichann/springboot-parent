package com.wt.springboot.designPatterns.creationalPatterns.Factory.FactoryMethod;

public class Bike implements Car {
    @Override
    public void gotoWork() {
        System.out.println("use bike to gotoWork");
    }
}
