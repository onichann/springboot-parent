package com.wt.springboot.designPatterns.creationalPatterns.Builder;

public class CharacterABuilder extends Builder {
    private  Product product;

    public CharacterABuilder(){
        this.product=new Product();
    }

    @Override
    public void buildName() {
        product.setName("角色A");
    }

    @Override
    public void buildClothes() {
        product.setClothes("漆黑的披风");
    }

    @Override
    public void buildWuqi() {
        product.setWuqi("咖喱棒");
    }

    @Override
    public Product create() {
        return product;
    }
}
