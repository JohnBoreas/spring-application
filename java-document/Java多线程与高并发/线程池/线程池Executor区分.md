ExecutorService（ThreadPoolExecutor的顶层接口）使用线程池中的线程执行每个提交的任务



Executors的工厂方法来创建ExecutorService。

线程池解决了两个不同的问题：

1. 提升性能：它们通常在执行大量异步任务时，由于减少了每个任务的调用开销，并且它们提供了一种限制和管理资源（包括线程）的方法，使得性能提升明显；
2. 统计信息：每个ThreadPoolExecutor保持一些基本的统计信息，例如完成的任务数量。

```
Executors.newCachedThreadPool（无界线程池，自动线程回收）
Executors.newFixedThreadPool（固定大小的线程池）；
Executors.newSingleThreadExecutor（单一后台线程）；
```



ThreadPoolExecutor 线程池，可用Executors创建



ScheduledExecutorService

是基于ExecutorService的功能实现的延迟和周期执行任务的功能，弥补了传统 Timer 的不足

```java
public interface ScheduledExecutorService extends ExecutorService {
    // 特定时间延时后执行一次Runnable
    public ScheduledFuture<?> schedule(Runnable command,
                                       long delay, TimeUnit unit);
    // 特定时间延时后执行一次Callable
    public <V> ScheduledFuture<V> schedule(Callable<V> callable,
                                           long delay, TimeUnit unit);
    // 固定周期执行任务（与任务执行时间无关，周期是固定的）
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
                                                  long initialDelay,
                                                  long period,
                                                  TimeUnit unit);
     // 固定延时执行任务（与任务执行时间有关，延时从上一次任务完成后开始）
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                     long initialDelay,
                                                     long delay,
                                                     TimeUnit unit);
}
```

