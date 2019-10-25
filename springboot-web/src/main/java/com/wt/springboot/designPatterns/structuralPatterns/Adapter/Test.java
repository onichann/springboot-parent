package com.wt.springboot.designPatterns.structuralPatterns.Adapter;

public class Test {
    public static void main(String[] args) {
        System.out.println("类适配器--");
        ClassAdapter classAdapter=new ClassAdapter();
        classAdapter.request();
        System.out.println("对象适配器--");
        ObjectAdapter objectAdapter=new ObjectAdapter(new Adaptee());
        objectAdapter.request();
    }
}
