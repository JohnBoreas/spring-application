

```java
public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);
```

主要流程：

根据initTime和period时间计算出第一次执行的时间差，

然后调ReentrantLock.newCondition().awaitNanos(long nanosTimeout)方法，到指定的时间进行唤醒，分配线程进行执行。

对于后续的周期性执行的await时间为period.

```java
// ScheduledThreadPoolExecutor
public RunnableScheduledFuture<?> take() throws InterruptedException {
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly();
    try {
        for (;;) {
            RunnableScheduledFuture<?> first = queue[0];
            if (first == null)
                available.await();
            else {
                long delay = first.getDelay(NANOSECONDS);
                if (delay <= 0)
                    return finishPoll(first);
                first = null; // don't retain ref while waiting
                if (leader != null)
                    available.await();
                else {
                    Thread thisThread = Thread.currentThread();
                    leader = thisThread;
                    try {
                        available.awaitNanos(delay);
                    } finally {
                        if (leader == thisThread)
                            leader = null;
                    }
                }
            }
        }
    } finally {
        if (leader == null && queue[0] != null)
            available.signal();
        lock.unlock();
    }
}
```

