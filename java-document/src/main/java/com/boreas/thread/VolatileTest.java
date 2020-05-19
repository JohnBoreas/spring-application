package com.boreas.thread;

/**
 * @author boreas
 * @create 2020-05-19 22:12
 */
public class VolatileTest {

    volatile boolean flag = true;

    /**
     * volatile可见性
     * @throws InterruptedException
     */
    public void visiblity() throws InterruptedException {
        new Thread(() -> {
            while (flag) {

            }
            System.out.println("end");
        }, "server").start();
        Thread.sleep(1000);
        flag = false;
    }


}
