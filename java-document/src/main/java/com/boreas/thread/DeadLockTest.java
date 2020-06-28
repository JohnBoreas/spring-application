package com.boreas.thread;

/**
 * @author boreas
 * @create 2020-06-26 2:20
 */
public class DeadLockTest {
    private static Object resourceOne = new Object();// 资源 1
    private static Object resourceTwo = new Object();// 资源 2

    public static void main(String[] args) {
        // 线程一对资源一进行请求，然后请求资源二
        // 线程二对资源二进行请求，然后请求资源一
        // 两个线程都在争抢彼此的资源
        new Thread(() -> {
            synchronized (resourceOne) {
                System.out.println(Thread.currentThread() + "get resourceOne");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resourceTwo");
                synchronized (resourceTwo) {
                    System.out.println(Thread.currentThread() + "get resourceTwo");
                }
            }
        }, "线程 1").start();

        new Thread(() -> {
            synchronized (resourceTwo) {
                System.out.println(Thread.currentThread() + "get resourceTwo");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resourceOne");
                synchronized (resourceOne) {
                    System.out.println(Thread.currentThread() + "get resourceOne");
                }
            }
        }, "线程 2").start();
    }
}
