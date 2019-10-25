package com.wt.springboot.designPatterns.creationalPatterns.Factory.FactoryMethod;

public class BusFactory implements CarFactory {
    @Override
    public Car getCar() {
        return new Bus();
    }
}
