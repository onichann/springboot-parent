package com.wt.springboot.designPatterns.creationalPatterns.Singleton;

/**
 * 类加载的过程是线程安全的，所以线程安全。
 */
public class StaticInner {
    private StaticInner(){

    }
    private static class InnerClass{
        private static StaticInner staticInner=new StaticInner();
    }
    public static StaticInner getInstance(){
        return InnerClass.staticInner;
    }
    public static void main(String[] args) {
        StaticInner staticInner=StaticInner.getInstance();
        StaticInner staticInner1=StaticInner.getInstance();
        System.out.println("StaticInner:"+(staticInner==staticInner1));
    }
}
