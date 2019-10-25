package com.wt.springboot.designPatterns.structuralPatterns.Composite;

import java.util.ArrayList;

public class FolderComponents extends AbstractComponent{

    public FolderComponents(String name,String value ,boolean isLeaf){
        super(name, value,isLeaf);
        this.subItems=new ArrayList<>();
    }
    public FolderComponents(){
        this.subItems=new ArrayList<>();
    }

    @Override
    public void add(AbstractComponent abstractComponent) {
        if(!this.containsComponent(abstractComponent)){
            this.subItems.add(abstractComponent);
        }
    }

    @Override
    public void delete(AbstractComponent abstractComponent) {

    }

    @Override
    public void display() {

    }

    @Override
    public boolean containsComponent(AbstractComponent abstractComponent) {
        boolean f=false;
        for(AbstractComponent component:this.subItems){
            if(abstractComponent.getValue().equals(component.getValue())){
                f=true;
                break;
            }
        }
        return f;
    }
}
