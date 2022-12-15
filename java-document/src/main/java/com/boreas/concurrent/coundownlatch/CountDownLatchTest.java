package com.boreas.concurrent.coundownlatch;

import java.util.concurrent.CountDownLatch;

/**
 */
class TaskThread extends Thread{
    private CountDownLatch latch ;

    public TaskThread(CountDownLatch latch){
        this.latch = latch;
    }
    public void run(){

        try {
            System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
            Thread.sleep(3000);
            System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
public class CountDownLatchTest {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);
        for(int i = 0;i < 2;i++){
            new TaskThread(latch).start();
        }
        System.out.println("等待两个子线程执行完毕");
        try {
            latch.await();
            System.out.println("两个子线程执行完毕");
            System.out.println("继续执行主线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
