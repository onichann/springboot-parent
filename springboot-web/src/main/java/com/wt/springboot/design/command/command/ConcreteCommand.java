package com.wt.springboot.design.command.command;


import com.wt.springboot.design.command.receiver.Receiver;

public class ConcreteCommand extends Command {

    public ConcreteCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public <R> R execute(Class<R> rClass) {
        return receiver.queryForObject(this.getKey(),rClass);
    }
}
