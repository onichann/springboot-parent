package com.wt.springboot.designPatterns.creationalPatterns.Builder;

public class Director {
    private Builder builder;
    public Director(Builder builder){
        this.builder=builder;
    }
    public Product builderProduct(){
        builder.buildName();
        builder.buildClothes();
        builder.buildWuqi();
        return builder.create();
    }
}
