package com.wt.springboot.designPatterns.creationalPatterns.Singleton;

public class Hungry {
    private static Hungry hungry=new Hungry();
    private Hungry() {

    }
    public static Hungry getInstance(){
        return hungry;
    }

    public static void main(String[] args) {
        Hungry hungry1=Hungry.getInstance();
        Hungry hungry2=Hungry.getInstance();
        System.out.println(hungry1==hungry2);
    }
}
