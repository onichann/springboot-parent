package com.wt.springboot.designPatterns.creationalPatterns.Singleton;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author wutong
 * @date 2020/8/4 11:05
 * introduction
 */
public class AQSSingleton {
    private static final AtomicReference<AQSSingleton> ATOMIC_REFERENCE = new AtomicReference<>();
    private AQSSingleton(){}
    public static AQSSingleton getInstance() {
        AQSSingleton aqsSingleton;
        do{
            aqsSingleton = ATOMIC_REFERENCE.get();
            if (aqsSingleton != null) {
                return aqsSingleton;
            }
            aqsSingleton = new AQSSingleton();
        }while (!ATOMIC_REFERENCE.compareAndSet(null, aqsSingleton));
        return aqsSingleton;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            System.out.println(AQSSingleton.getInstance()==AQSSingleton.getInstance());
        }
    }
}
