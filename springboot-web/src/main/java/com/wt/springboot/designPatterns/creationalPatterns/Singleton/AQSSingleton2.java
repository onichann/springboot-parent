package com.wt.springboot.designPatterns.creationalPatterns.Singleton;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Pat.Wu
 * @date 2021/10/15 9:38
 * @description
 */
public class AQSSingleton2 {
    private static final AtomicReference<AQSSingleton2> atomicReference = new AtomicReference<AQSSingleton2>();
    private AQSSingleton2() {

    }

    public static AQSSingleton2 getInstance() {
        for (; ; ) {
            AQSSingleton2 instance = atomicReference.get();
            if (instance != null) {
                return instance;
            }
            instance = new AQSSingleton2();
            if (atomicReference.compareAndSet(null, instance)) {
                return instance;
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(AQSSingleton2.getInstance().equals(AQSSingleton2.getInstance()));
        }
    }


}
