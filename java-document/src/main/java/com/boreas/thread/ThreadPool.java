package com.boreas.thread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author boreas
 * @create 2022-02-24 14:11
 */
public class ThreadPool {

    public static void main(String[] args) {
        // 创建线程池 ， 参数含义 ：（核心线程数，最大线程数，加开线程的存活时间，时间单位，任务队列长度）
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 8,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>());

        //设置a的值范围在：a = （corePoolSize-1） ～ (max+queue+1) ,分析：任务数 与 活跃线程数,核心线程数,队列长度,最大线程数的关系。
        int a = 10;

        for (int i = 1; i <= a; i++) {
            int j = i;
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    //获取线程名称
                    Thread thread = Thread.currentThread();
                    String name = thread.getName();
                    int activeCount = pool.getActiveCount();
                    System.out.println("任务：" + j + "-----,线程名称:" + name + "-----活跃线程数:" + activeCount);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            // 输出
            int activeCount = pool.getActiveCount();
            System.out.println("任务：" + j + "-----活跃线程数:" + activeCount);
        }
        //关闭线程池
        pool.shutdown();
    }
}
