package com.wt.springboot.design.command2.command;


import java.io.Serializable;

public abstract class Command<T extends Serializable> {

    public abstract <R> R execute(Class<R> tClass);

}
