package com.wt.springboot.designPatterns.creationalPatterns.Singleton;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pat.Wu
 * @date 2021/10/14 17:36
 * @description
 */
public class LockSingleton {
    private static volatile LockSingleton instance;
    private static ReentrantLock lock = new ReentrantLock();
    private LockSingleton() {}
    public static LockSingleton getInstance(){
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new LockSingleton();
                }
            }finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i <10; i++) {
            System.out.println(LockSingleton.getInstance()==LockSingleton.getInstance());
        }
    }
}
