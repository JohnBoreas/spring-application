package com.boreas.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * @author xuhua.jiang
 * @date 2020-7-07
 */
public class ExecutorsTest {
    public static void main(String[] args) {
        ExecutorsTest test = new ExecutorsTest();
        test.singleThreadPool();
    }

    public void fixedThreadPool() {
        // 固定数量线程池
        // new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        ExecutorService service = Executors.newFixedThreadPool(5); //execute submit
        for (int i = 0; i < 5; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println(service);
        service.shutdown();
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);
    }

    public void cachedThreadPool() {
        // 新来的一个任务需要这个线程池来执行的话，如果当前线程池没有闲置的线程那么就新启动一个线程，如果有空闲线程那么就使用其中的一个空闲线程。
        // 有弹性的线程池。默认线程空闲超过60s会销毁
        // new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        service.shutdown();
    }

    public void singleThreadPool() {
        // 可被复用的单线程
        // new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()));
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
    }


}
