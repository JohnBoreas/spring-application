package com.boreas.threadpool;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 线程池
 *
 * @author xuhua.jiang
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {

    }

    public void threadPoolExecutorParams() {
        /**
         * Nthreads=CPU数量
         * Ucpu=目标CPU的使用率，0<=Ucpu<=1
         * W/C=任务等待时间与任务计算时间的比率
         */
        // 线程数量: Nthreads = Ncpu*Ucpu*(1+W/C)
        // 实现自定义接口
        ExecutorService pool = new ThreadPoolExecutor(2, 4, 1000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5),
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        System.out.println("线程" + r.hashCode() + "创建");
                        //线程命名
                        Thread th = new Thread(r, "threadPool" + r.hashCode());
                        return th;
                    }
                }, new ThreadPoolExecutor.CallerRunsPolicy()) {
            // beforeExecute()、afterExecute()和terminated()的实现，我们对线程池中线程的运行状态进行了监控
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行：" + t.getName());
            }

            protected void afterExecute(Runnable r, Throwable t) {
                Date startDate = new Date();
                Date finishDate = new Date();
                long diff = finishDate.getTime() - startDate.getTime();
                // 统计任务耗时、初始线程数、核心线程数、正在执行的任务数量、
                // 已完成任务数量、任务总数、队列里缓存的任务数量、
                // 池中存在的最大线程数、最大允许的线程数、线程空闲时间、线程池是否关闭、线程池是否终止
                System.out.println("任务耗时:" + diff);
                System.out.println("初始线程数:" + this.getPoolSize());
                System.out.println("核心线程数:" + this.getCorePoolSize());
                System.out.println("正在执行的任务数量:" + this.getActiveCount());
                System.out.println("已经执行的任务数:" + this.getCompletedTaskCount());
                System.out.println("任务总数:" + this.getTaskCount());
                System.out.println("最大允许的线程数:" + this.getMaximumPoolSize());
                System.out.println("线程空闲时间:" + this.getKeepAliveTime(TimeUnit.MILLISECONDS));
                System.out.println();
                System.out.println("执行完毕：" + t.getMessage());
            }

            protected void terminated() {
                System.out.println("线程池退出");
            }

            public void shutdown() {
                System.out.println("已经执行的任务数：" + this.getCompletedTaskCount() + ", " +
                        "当前活动线程数:" + this.getActiveCount() + ",当前排队线程数:" + this.getQueue().size());
                System.out.println();
                super.shutdown();
            }
        };
    }
}
