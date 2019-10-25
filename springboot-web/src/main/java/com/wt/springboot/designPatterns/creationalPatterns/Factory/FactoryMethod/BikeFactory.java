package com.wt.springboot.designPatterns.creationalPatterns.Factory.FactoryMethod;

public class BikeFactory implements CarFactory {
    @Override
    public Car getCar() {
        return new Bike();
    }
}
