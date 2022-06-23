package com.wt.springboot.designPatterns.structuralPatterns.Flyweight.singleton;

/**
 * @author wutong
 * @date 2022/6/23 14:07
 * 说明:
 */
public class ConcreteFlyweight implements Flyweight {
    private String innerState = null;

    /**
     * **
     *     * 构造函数，内蕴状态作为参数传入
     *     * @param state
     *
     * @param innerState
     */
    public ConcreteFlyweight(String innerState) {
        this.innerState = innerState;
    }

    /**
     * 外蕴状态作为参数传入方法中，改变方法的行为，
     * 但是并不改变对象的内蕴状态。
     */
    @Override
    public void operation(String state) {

        System.out.println("Intrinsic State = " + this.innerState);
        System.out.println("Extrinsic State = " + state);
    }
}
