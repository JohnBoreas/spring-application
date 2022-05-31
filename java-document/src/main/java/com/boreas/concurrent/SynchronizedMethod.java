package com.boreas.concurrent;

/**
 * @author boreas
 * @create 2022-05-31 13:15
 */
public class SynchronizedMethod {

    public synchronized void methodOne() {
        try {
            Thread.sleep(100000);
            System.out.println("methodOne");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void methodTwo() {
        try {
            Thread.sleep(100000);
            System.out.println("methodTwo");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void methodThird() {
        try {
            Thread.sleep(100000);
            System.out.println("methodThird");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
