package com.wt.springboot.designPatterns.structuralPatterns.Adapter;

public class ClassAdapter extends Adaptee implements Target {
    @Override
    public void request() {
        super.specificRequest();
    }
}
