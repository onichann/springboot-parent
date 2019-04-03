package com.wt.springboot.design.command2.invoker;

import java.io.Serializable;

public interface Invoker<T extends Serializable> {

    public <R> R invoker(Class<R> tClass);

}
