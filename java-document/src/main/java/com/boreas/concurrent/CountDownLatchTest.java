package com.boreas.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author boreas
 * @create 2020-04-13 17:21
 */
public class CountDownLatchTest {
    public void countDownLatchTest() {
        // CountDownLatch : 当多个线程都达到预期状态或完成预期工作时触发事件
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10));
        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        int[] datas = new int[10204];
        int step = datas.length / count;
        for (int i = 0; i < count; i++) {
            int start = i * step;
            int end = (i + 1) * step;
            if (i == count - 1) {
                end = datas.length;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
