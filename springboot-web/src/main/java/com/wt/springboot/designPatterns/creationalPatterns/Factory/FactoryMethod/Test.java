package com.wt.springboot.designPatterns.creationalPatterns.Factory.FactoryMethod;

public class Test {
    public static void main(String[] args) {
         CarFactory carFactory=null;
         //bike
        carFactory=new BikeFactory();
        Car car=carFactory.getCar();
        car.gotoWork();
        //bus
        carFactory=new BusFactory();
        Car car1=carFactory.getCar();
        car1.gotoWork();
    }
}
