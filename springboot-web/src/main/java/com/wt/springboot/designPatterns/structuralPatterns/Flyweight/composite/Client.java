package com.wt.springboot.designPatterns.structuralPatterns.Flyweight.composite;

import com.wt.springboot.designPatterns.structuralPatterns.Flyweight.singleton.Flyweight;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wutong
 * @date 2022/6/23 15:02
 * 说明:
 */
public class Client {
    public static void main(String[] args) {
        List<String> compositeState = new ArrayList<>();
        compositeState.add("a");
        compositeState.add("b");
        compositeState.add("c");
        compositeState.add("a");
        compositeState.add("b");
        FlyweightFactory flyFactory = new FlyweightFactory();
        Flyweight compositeFly1 = flyFactory.factory(compositeState);
        Flyweight compositeFly2 = flyFactory.factory(compositeState);
        compositeFly1.operation("Composite Call");
        System.out.println("---------------------------------");
        System.out.println("复合享元模式是否可以共享对象：" + (compositeFly1 == compositeFly2));

        String state = "a";
        Flyweight fly1 = flyFactory.factory(state);
        Flyweight fly2 = flyFactory.factory(state);
        System.out.println("单纯享元模式是否可以共享对象：" + (fly1 == fly2));

    }
}
