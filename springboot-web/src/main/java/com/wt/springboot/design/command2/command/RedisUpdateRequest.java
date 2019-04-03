package com.wt.springboot.design.command2.command;


import com.wt.springboot.design.command2.receiver.Receiver;

import java.io.Serializable;

public class RedisUpdateRequest<T extends Serializable> extends Command<T> {

    private String key;
    private Receiver<T> receiver;
    private Object obj;

    public RedisUpdateRequest(String key,Object obj, Receiver<T> receiver) {
        this.key = key;
        this.receiver=receiver;
        this.obj=obj;
    }

    @Override
    public <R> R execute(Class<R> t1Class) {
        return (R) receiver.updateObject(key,obj,t1Class);
    }
}
