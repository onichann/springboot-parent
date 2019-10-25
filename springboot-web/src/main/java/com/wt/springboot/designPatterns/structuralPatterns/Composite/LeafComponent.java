package com.wt.springboot.designPatterns.structuralPatterns.Composite;

import java.util.ArrayList;

public class LeafComponent extends AbstractComponent {

    public LeafComponent(String name, String value,boolean isLeaf){
        super(name,value,isLeaf);
        this.subItems=new ArrayList<>();
    }

    public LeafComponent() {
        this.subItems=new ArrayList<>();

    }

    @Override
    public void add(AbstractComponent abstractComponent) {

    }

    @Override
    public void delete(AbstractComponent abstractComponent) {

    }

    @Override
    public void display() {

    }

    @Override
    public boolean containsComponent(AbstractComponent abstractComponent) {
        return false;
    }
}
