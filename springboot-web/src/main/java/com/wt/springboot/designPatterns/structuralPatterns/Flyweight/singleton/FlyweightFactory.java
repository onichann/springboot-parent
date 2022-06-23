package com.wt.springboot.designPatterns.structuralPatterns.Flyweight.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wutong
 * @date 2022/6/23 14:10
 * 说明:
 */
public class FlyweightFactory {
    private Map<String, Flyweight> flyweightMap = new ConcurrentHashMap<>();

    public Flyweight factory(String state) {
        Flyweight flyweight = flyweightMap.get(state);
        if (flyweight == null) {
            flyweight = new ConcreteFlyweight(state);
            flyweightMap.put(state, flyweight);
        }
        return flyweight;
    }
}
