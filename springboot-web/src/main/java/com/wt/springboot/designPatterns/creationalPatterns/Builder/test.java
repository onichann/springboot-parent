package com.wt.springboot.designPatterns.creationalPatterns.Builder;

public class test {

    public static void main(String[] args) {
        Builder builder=new CharacterABuilder();
        Director director=new Director(builder);
        Product product=director.builderProduct();
        System.out.println("product = " + product.toString());
    }
}
