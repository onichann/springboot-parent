package com.wt.springboot.design.command2.receiver;

import com.alibaba.fastjson.JSON;
import com.wt.springboot.design.command.Product;

import java.io.Serializable;

public class RedisReceiver<T extends Serializable> implements Receiver<T> {

    @Override
    public <R> R queryForObject(String key, Class<R> tClass) {
        if(!isInRedis(key)){
            return null;
        }
        Product product=Product.builder().id(key).name("name").price(100).build();
        return JSON.parseObject(JSON.toJSONString(product),tClass);
    }

    @Override
    public boolean isInRedis(String key) {
        //todo...
        return true;
    }

    @Override
    public <R> R updateObject(String key, Object object,Class<R> tClass) {
        //todo...
        int i=1;
        return JSON.parseObject(JSON.toJSONString(i),tClass);
    }
}
