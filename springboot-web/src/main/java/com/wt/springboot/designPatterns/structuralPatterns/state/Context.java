package com.wt.springboot.designPatterns.structuralPatterns.state;

/**
 * @author wutong
 * @date 2022/6/30 16:16
 * 说明:
 */
public class Context {

    private State state;

    private double bill;

    public void Handle(){
        checkState();
        state.behavior();
    }

    private void checkState(){
        if(bill >= 0.00){
            setState(new ConcreteStateA());
        } else {
            setState(new ConcreteStateB());
        }
    }

    private void setState(State state){
        this.state = state;
    }


    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }
}
