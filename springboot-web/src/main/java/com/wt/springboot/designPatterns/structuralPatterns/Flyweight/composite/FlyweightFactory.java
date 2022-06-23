package com.wt.springboot.designPatterns.structuralPatterns.Flyweight.composite;

import com.wt.springboot.designPatterns.structuralPatterns.Flyweight.singleton.ConcreteFlyweight;
import com.wt.springboot.designPatterns.structuralPatterns.Flyweight.singleton.Flyweight;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wutong
 * @date 2022/6/23 14:10
 * 说明:
 */
public class FlyweightFactory {
    private Map<String, Flyweight> flyweightMap = new ConcurrentHashMap<>();

    //魔改
    private Map<String, CompositeConcreteFlyweight> comositeflyweightMap = new ConcurrentHashMap<>();

    /**
     * 复合享元工厂方法 魔改 用内蕴状态做key，可以保证复合享元能够共享，减少对象创建
     *
     * @param state
     * @return
     */

    public CompositeConcreteFlyweight factory(List<String> state) {
        String allState = String.join(",", state);
        CompositeConcreteFlyweight flyweight = comositeflyweightMap.get(allState);
        if (flyweight == null) {
            CompositeConcreteFlyweight compositeConcreteFlyweight = new CompositeConcreteFlyweight();
            for (String s : state) {
                compositeConcreteFlyweight.add(s, factory(s));
            }
            flyweight = compositeConcreteFlyweight;
            comositeflyweightMap.put(allState, compositeConcreteFlyweight);
        }

        return flyweight;
    }

    /**
     * 原本
     * @param state
     * @return
     */
//    public CompositeConcreteFlyweight factory(List<String> state) {
//            CompositeConcreteFlyweight compositeConcreteFlyweight = new CompositeConcreteFlyweight();
//            for (String s : state) {
//                compositeConcreteFlyweight.add(s, factory(s));
//            }
//        return compositeConcreteFlyweight;
//
//    }

    public Flyweight factory(String state) {
        Flyweight flyweight = flyweightMap.get(state);
        if (flyweight == null) {
            flyweight = new ConcreteFlyweight(state);
            flyweightMap.put(state, flyweight);
        }
        return flyweight;
    }
}
