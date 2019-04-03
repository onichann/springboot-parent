package com.wt.springboot.design.command.receiver;

import com.alibaba.fastjson.JSON;
import com.wt.springboot.design.command.Product;


public class Receiver {

    public <T> T queryForObject(String Key,Class<T> tClass){
        Product product = Product.builder().id(Key).name("name").price(100).build();
        return JSON.parseObject(JSON.toJSONString(product),tClass);
    }

    public int updateObject(String key,Object data){
        return 1;
    }
}
