package com.wt.springboot.designPatterns.structuralPatterns.Composite;

import java.util.List;

public abstract class AbstractComponent {

    private String name;
    private String value;
    protected List<AbstractComponent> subItems;
    private boolean isLeaf;

    public AbstractComponent(String name, String value,boolean isLeaf) {
        this.name = name;
        this.value = value;
        this.isLeaf=isLeaf;
    }
    public AbstractComponent(){}

    public abstract void add(AbstractComponent abstractComponent);
    public abstract void delete(AbstractComponent abstractComponent);
    public abstract void display();
    public abstract boolean containsComponent(AbstractComponent abstractComponent);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<AbstractComponent> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<AbstractComponent> subItems) {
        this.subItems = subItems;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }
}
