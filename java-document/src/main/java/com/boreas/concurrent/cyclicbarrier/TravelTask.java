package com.boreas.concurrent.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 游客：
 * 导游：
 *    所有的游客到达同一个地点后发签证，才能去旅游
 */

public class TravelTask implements Runnable{

    private CyclicBarrier cyclicBarrier;
    private String name;
    private int arriveTime;//赶到的时间

    public TravelTask(CyclicBarrier cyclicBarrier,String name,int arriveTime){
        this.cyclicBarrier = cyclicBarrier;
        this.name = name;
        this.arriveTime = arriveTime;
    }

    @Override
    public void run() {
        try {
            //模拟达到需要花的时间
            // count统计 初始化：count = parties
            // 一个线程到达屏障点后：count--  -->count == 0 --> 所有的线程都到达屏障点
            Thread.sleep(arriveTime * 1000);
            System.out.println(name +"到达集合点");
            System.out.println("正在等待的人数:"+cyclicBarrier.getNumberWaiting());
            cyclicBarrier.await();
            System.out.println(name +"开始旅行啦～～");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
