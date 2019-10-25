package com.wt.springboot.designPatterns.structuralPatterns.Decorator;

public class AbstractPerson implements Person {

    private Person person;

    public AbstractPerson(Person person) {
        this.person = person;
    }

    @Override
    public void eat() {
        person.eat();
    }
}
