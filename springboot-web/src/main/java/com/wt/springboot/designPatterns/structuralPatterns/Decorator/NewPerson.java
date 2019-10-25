package com.wt.springboot.designPatterns.structuralPatterns.Decorator;

public class NewPerson extends AbstractPerson {
    public NewPerson(Person person) {
        super(person);
    }

    @Override
    public void eat() {
        System.out.println("做红烧肉");
        super.eat();
        System.out.println("洗碗");
    }
}
