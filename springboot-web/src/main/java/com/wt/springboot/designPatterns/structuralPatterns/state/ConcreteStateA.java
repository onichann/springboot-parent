package com.wt.springboot.designPatterns.structuralPatterns.state;

/**
 * @author wutong
 * @date 2022/6/30 16:15
 * 说明:
 */
public class ConcreteStateA extends State{
    @Override
    public void behavior() {
        System.out.println("手机在未欠费停机状态下, 能正常拨打电话");
    }
}
