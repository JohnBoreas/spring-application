package com.boreas.concurrent;

import java.util.concurrent.*;

/**
 * @author boreas
 * @create 2020-04-13 17:30
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrierTest test = new CyclicBarrierTest();
        try {
            test.cyclicBarrierTest();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * CyclicBarrier 循环屏障,让多个线程在这个屏障前等待，直到所有线程都达到这个屏障时，再一起继续执行后面的动作
     */
    public void cyclicBarrierTest() throws BrokenBarrierException, InterruptedException {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(9,9,60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10));
        int count = 9;
        CyclicBarrier barrier = new CyclicBarrier(count);
        int[] datas = new int[10204];
        int step = datas.length / count;
        for (int i = 0; i < count; i++) {
            int start = i * step;
            int end = (i + 1) * step;
            if (i == count - 1) {
                end = datas.length;

            }
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("正在等待的人数:"+barrier.getNumberWaiting());
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println("开始旅行啦～～");
                }
            });
            System.out.println(start + "_" + end);
        }
        threadPool.shutdown();
    }
}
