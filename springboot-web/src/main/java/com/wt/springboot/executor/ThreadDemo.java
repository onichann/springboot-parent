package com.wt.springboot.executor;

/**
 * @author Administrator
 * @date 2019-05-20 上午 11:21
 * PROJECT_NAME springboot-parent
 */
public class ThreadDemo extends Thread {

    public ThreadDemo(Runnable target) {
        super(target);
    }

    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo(new Runnable(){
            @Override
            public void run() {

            }
        });
        threadDemo.setDaemon(true);
        threadDemo.start();
    }
}