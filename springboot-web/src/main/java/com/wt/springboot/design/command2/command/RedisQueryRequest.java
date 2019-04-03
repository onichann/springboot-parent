package com.wt.springboot.design.command2.command;


import com.wt.springboot.design.command2.receiver.Receiver;

import java.io.Serializable;

public class RedisQueryRequest<T extends Serializable> extends Command<T> {

    private String key;
    private Receiver<T> receiver;

    public RedisQueryRequest(String key, Receiver<T> receiver) {
        this.key = key;
        this.receiver=receiver;
    }

    @Override
    public <R> R execute(Class<R> tClass) {
        return receiver.queryForObject(key, tClass);
    }
}
