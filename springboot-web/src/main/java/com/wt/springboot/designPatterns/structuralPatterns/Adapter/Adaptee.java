package com.wt.springboot.designPatterns.structuralPatterns.Adapter;

public class Adaptee {

    protected void specificRequest(){
        System.out.println("这个是源角色");
    }
}
