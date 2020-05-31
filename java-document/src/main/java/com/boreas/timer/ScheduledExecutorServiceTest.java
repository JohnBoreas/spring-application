package com.boreas.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author boreas
 * 1.相比于Timer的单线程，它是通过线程池的方式来执行任务的
 * 2.可以很灵活的去设定第一次执行任务delay时间
 * 3.提供了良好的约定，以便设定执行的时间间隔
 * @create 2020-05-25 23:01
 */
public class ScheduledExecutorServiceTest {

    public void scheduledExecutor() {
        /**
         * Runnable：实现了Runnable接口，jdk就知道这个类是一个线程
         */
        Runnable runnable = new Runnable() {
            //创建 run 方法
            public void run() {
                // task to run goes here
                System.out.println("Hello, stranger");
            }
        };
        // ScheduledExecutorService:是从Java SE5的java.util.concurrent里，
        // 做为并发工具类被引进的，这是最理想的定时任务实现方式。
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        // 10：秒   5：秒
        // 第一次执行的时间为10秒，然后每隔五秒执行一次
        service.scheduleAtFixedRate(runnable, 10, 5, TimeUnit.SECONDS);
    }
}
