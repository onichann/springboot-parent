package com.wt.springboot.designPatterns.creationalPatterns.Singleton;

public class LazyCreate {
    private static LazyCreate lazyCreate;
    private LazyCreate() {

    }
    public static synchronized LazyCreate getInstance(){
        if (lazyCreate == null) {
            lazyCreate=new LazyCreate();
        }
        return lazyCreate;
    }

    public static void main(String[] args) {
        LazyCreate lazyCreate=LazyCreate.getInstance();
        LazyCreate lazyCreate1=lazyCreate.getInstance();
        System.out.println("LzayCreate:"+(lazyCreate==lazyCreate1));
    }
}
