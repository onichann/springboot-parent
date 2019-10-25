package com.wt.springboot.designPatterns.creationalPatterns.Builder;

public abstract class Builder {
    public abstract void buildName();
    public abstract void buildClothes();
    public abstract void buildWuqi();
    public abstract Product create();

}
