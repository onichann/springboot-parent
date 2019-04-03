package com.wt.springboot.design.command2.receiver;

import java.io.Serializable;

public interface Receiver<T extends Serializable> {

    public <R> R queryForObject(String key, Class<R> tClass);

    public boolean isInRedis(String key);

    public <R> R updateObject(String key, Object object, Class<R> tClass);

}
