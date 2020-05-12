1、Timer

```java
Timer timer = new Timer();
TimerTask task = new TimerTask() {
    @Override
    public void run() {
        // 执行的内容
    }
};
//安排指定的任务在指定的时间开始进行重复的固定延迟执行。
timer.schedule(task, date, PERIOD_DAY);// 时间间隔
```

timer源码：

```java
public class Timer {
    // 1. Timer 中维护了一个TaskQueue队列，存放TimerTask任务 
    // 2. Timer 定义了一个线程，用于执行轮询队列中的Task任务，并执行。
    /*** The timer task queue. */
    private final TaskQueue queue = new TaskQueue();
    /*** The timer thread.*/
    private final TimerThread thread = new TimerThread(queue);
    public Timer() {
        this("Timer-" + serialNumber());
    }
    public Timer(String name) {// timer在创建对象的时候就已经启动了一个线程
        thread.setName(name);
        thread.start();
    }
}
```

TaskQueue:

```java
// TaskQueue 内部维护了一个TimerTask数组。 
// TimreTask 数字中存放了所有的定时任务。 
// TimerTask[] 数组是从下标1开始存放元素的。 
// 即将要执行的任务永远存放到TimerTask[1] 中，（数组中的任务顺序，是不断的调整的，每次获取完任务后都会调整一次）。
class TaskQueue {
    /***/
    private TimerTask[] queue = new TimerTask[128];
    TimerTask getMin() { return queue[1]; }
}
```

TimerThread.mainLoop()

timer是一个死循环程序，除非遇到不能捕获的异常或break才会跳出

```java
// 主要逻辑是：
// 1.从queue中获取将要执行task1 (TimerTask[1]=task1, TimerTask[2]=task2).
// 2.获取完成后，然后把queue的TimerTask[1] =task2，TimerTask[2]=task1
// 3.然后执行获取的task.run()。
/**
  * The main timer loop.  (See class comment.)
  */
private void mainLoop() {
    while (true) {
        try {
            TimerTask task;
            boolean taskFired;
            synchronized(queue) {
                // Wait for queue to become non-empty
                while (queue.isEmpty() && newTasksMayBeScheduled)
                    queue.wait();
                if (queue.isEmpty())
                    break; // Queue is empty and will forever remain; die

                // Queue nonempty; look at first evt and do the right thing
                long currentTime, executionTime;
                task = queue.getMin();
                synchronized(task.lock) {
                    if (task.state == TimerTask.CANCELLED) {
                        queue.removeMin();
                        continue;  // No action required, poll queue again
                    }
                    currentTime = System.currentTimeMillis();
                    executionTime = task.nextExecutionTime;
                    if (taskFired = (executionTime<=currentTime)) {
                        if (task.period == 0) { // Non-repeating, remove
                            queue.removeMin();
                            task.state = TimerTask.EXECUTED;
                        } else { // Repeating task, reschedule
                            queue.rescheduleMin(
                                task.period<0 ? currentTime   - task.period
                                : executionTime + task.period);
                        }
                    }
                }
                if (!taskFired) // Task hasn't yet fired; wait
                    queue.wait(executionTime - currentTime);
            }
            if (taskFired)  // Task fired; run it, holding no locks
                task.run();
        } catch(InterruptedException e) {
        }
    }
}
```

二、缺陷

1、同一timer下放多个task，会延迟

原因：Timer底层是使用一个单线来实现多个Timer任务处理的，所有任务都是由同一个线程来调度，所有任务都是串行执行，意味着同一时间只能有一个任务得到执行，而前一个任务的延迟或者异常会影响到之后的任务。

2、一个任务异常，后面任务均受影响

因为Timer中所有的任务都是在一个线程中执行，那么如果有一个定时任务在运行时，产生未处理的异常，那么当前这个线程就会停止，那么所有的定时任务都会停止，受到影响。