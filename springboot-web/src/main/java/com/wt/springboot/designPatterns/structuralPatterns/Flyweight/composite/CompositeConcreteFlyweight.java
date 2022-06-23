package com.wt.springboot.designPatterns.structuralPatterns.Flyweight.composite;

import com.wt.springboot.designPatterns.structuralPatterns.Flyweight.singleton.Flyweight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wutong
 * @date 2022/6/23 14:51
 * 说明:
 */
public class CompositeConcreteFlyweight implements Flyweight {

    private Map<String, Flyweight> concretefly = new ConcurrentHashMap<>();

    public void add(String state, Flyweight flyweight) {
        concretefly.put(state, flyweight);
    }


    @Override
    public void operation(String state) {
        Flyweight fly = null;
        for(String o : concretefly.keySet()){
            fly = concretefly.get(o);
            fly.operation(state);
        }
    }
}
