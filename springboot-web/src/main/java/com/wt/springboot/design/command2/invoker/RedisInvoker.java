package com.wt.springboot.design.command2.invoker;


import com.wt.springboot.design.command2.command.Command;

import java.io.Serializable;

public class RedisInvoker<T extends Serializable> implements Invoker<T> {

    private Command<T> command;

    public RedisInvoker(Command command) {
        this.command = command;
    }

    @Override
    public <R> R invoker(Class<R> t1Class) {
        return command.execute(t1Class);
    }
}
