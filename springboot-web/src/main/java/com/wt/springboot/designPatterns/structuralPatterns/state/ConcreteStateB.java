package com.wt.springboot.designPatterns.structuralPatterns.state;

/**
 * @author wutong
 * @date 2022/6/30 16:15
 * 说明:
 */
public class ConcreteStateB extends State{
    @Override
    public void behavior() {
        System.out.println("手机在欠费停机状态下, 不能拨打电话");
    }
}
