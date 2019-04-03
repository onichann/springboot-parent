package com.wt.springboot;

public class OverRideTest {

    public class A{
        private void set(){
            System.out.println("set");
        }
    }

    public class B extends A{
        //重写时子类的访问权限不能比父类小
        //子类在重写父类抛出异常的方法时，要么不抛出异常，要么抛出与父类方法相同的异常或该异常的子类。如果被重写的父类方法只抛出受检异常，则子类重写的方法可以抛出非受检异常。例如，父类方法抛出了一个受检异常IOException，重写该方法时不能抛出Exception，对于受检异常而言，只能抛出IOException及其子类异常，也可以抛出非受检异常。
        protected void set() {
            System.out.println("set");
        }
    }
}
