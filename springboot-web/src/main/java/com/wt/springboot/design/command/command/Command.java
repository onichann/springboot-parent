package com.wt.springboot.design.command.command;


import com.wt.springboot.design.command.receiver.Receiver;
import lombok.Getter;
import lombok.Setter;

public abstract class Command {

    @Setter
    @Getter
    public String key;
    @Setter
    @Getter
    public Object data;

    @Getter
    public Receiver receiver;

    public Command(Receiver receiver) {
        this.receiver = receiver;
    }

    public abstract <R> R execute(Class<R> rClass);

}
