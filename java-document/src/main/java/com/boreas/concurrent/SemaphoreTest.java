package com.boreas.concurrent;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author boreas
 * @create 2022-03-09 23:41
 */
public class SemaphoreTest {

    public static void main(String[] args) {

    }

    public void executor() {
        Semaphore semaphore = new Semaphore(1);
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        System.out.println("====" + Thread.currentThread().getName() + "来到停车场");
                        if (semaphore.availablePermits() == 0) {
                            System.out.println("车位不足，请耐心等待");
                        }
                        // 获取令牌尝试进入停车场
                        if (semaphore.tryAcquire(1000, TimeUnit.MICROSECONDS)) {
                            // 模拟车辆在停车场停留的时间
                            System.out.println(Thread.currentThread().getName() + "成功进入停车场");
                            Thread.sleep(new Random().nextInt(10000));
                        }
                        System.out.println(Thread.currentThread().getName() + "驶出停车场");
                        semaphore.release();// 释放令牌，腾出停车场车位
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, i + "号车");
            thread.start();
        }
    }
}
