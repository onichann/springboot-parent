package com.wt.springboot.designPatterns.behavioralPatterns.Observer;

import java.util.Vector;

public abstract class Subject {
    private Vector<Observer> observers=new Vector<>();
    public void addObserver(Observer observer){
        this.observers.add(observer);
    }
    public void delObserver(Observer observer){
        this.observers.remove(observer);
    }

    protected void notifyObserver(){
        for(Observer o: observers){
            o.update();
        }
    }

    public abstract void doSomething();
}
