package com.boreas.concurrent.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 */
public class Client {

    public static void main(String[] args) throws Exception{

        // 第一个参数：相互等待的线程数量（方数）
        // 第二个参数：当最后一个线程到达屏障点的时候所有做的事情
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,new TourGuideTask());
        System.out.println("开启屏障方数:"+cyclicBarrier.getParties());
        Executor executor = Executors.newFixedThreadPool(3);
        executor.execute(new TravelTask(cyclicBarrier,"哈登",5));
        executor.execute(new TravelTask(cyclicBarrier,"保罗",3));
        executor.execute(new TravelTask(cyclicBarrier,"戈登",1));

    }

}