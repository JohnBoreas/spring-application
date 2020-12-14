###### execute()

```java
public void execute(Runnable command) {
    //(1) 如果任务为null，则抛出NPE异常
	if (command == null)
		throw new NullPointerException();
    //（2）获取当前线程池的状态+线程个数变量的组合值
	int c = ctl.get();
	if (workerCountOf(c) < corePoolSize) {//1.当前池中线程比核心数少，新建一个线程执行任务
		if (addWorker(command, true))
			return;
		c = ctl.get();
	}
	if (isRunning(c) && workQueue.offer(command)) {//2.核心池已满，但任务队列未满，添加到队列中
		int recheck = ctl.get();
		//任务成功添加到队列以后，再次检查是否需要添加新的线程，因为已存在的线程可能被销毁了
		if (! isRunning(recheck) && remove(command))
            //如果线程池处于非运行状态，并且把当前的任务从任务队列中移除成功，则拒绝该任务
			reject(command);
		else if (workerCountOf(recheck) == 0)//如果之前的线程已被销毁完，新建一个线程
			addWorker(null, false);
	} else if (!addWorker(command, false)) //3.核心池已满，队列已满，试着创建一个新线程
		reject(command); //如果创建新线程失败了，说明线程池被关闭或者线程池完全满了，拒绝任务
}
```

###### addWorkder()

```java
private boolean addWorker(Runnable firstTask, boolean core) {
    retry:
    for (;;) {
        int c = ctl.get();
        int rs = runStateOf(c);
        //（6） 检查队列是否只在必要时为空
        if (rs >= SHUTDOWN &&
            ! (rs == SHUTDOWN &&
               firstTask == null &&
               ! workQueue.isEmpty()))
            return false;
        //（7）循环cas增加线程个数
        for (;;) {
            int wc = workerCountOf(c);
            //（7.1）如果线程个数超限则返回false
            if (wc >= CAPACITY ||
                wc >= (core ? corePoolSize : maximumPoolSize))
                return false;
            //（7.2）cas增加线程个数，同时只有一个线程成功
            if (compareAndIncrementWorkerCount(c))
                break retry;
            //（7.3）cas失败了，则看线程池状态是否变化了，变化则跳到外层循环重试重新获取线程池状态，否者内层循环重新cas。
            c = ctl.get();  // Re-read ctl
            if (runStateOf(c) != rs)
                continue retry;
        }
    }

    //（8）到这里说明cas成功了
    boolean workerStarted = false;
    boolean workerAdded = false;
    Worker w = null;
    try {
        //（8.1）创建worker
        final ReentrantLock mainLock = this.mainLock;
        w = new Worker(firstTask);
        final Thread t = w.thread;
        if (t != null) {

            //（8.2）加独占锁，为了workers同步，因为可能多个线程调用了线程池的execute方法。
            mainLock.lock();
            try {

                //（8.3）重新检查线程池状态，为了避免在获取锁前调用了shutdown接口
                int c = ctl.get();
                int rs = runStateOf(c);

                if (rs < SHUTDOWN ||
                    (rs == SHUTDOWN && firstTask == null)) {
                    if (t.isAlive()) // precheck that t is startable
                        throw new IllegalThreadStateException();
                    //（8.4）添加任务
                    workers.add(w);
                    int s = workers.size();
                    if (s > largestPoolSize)
                        largestPoolSize = s;
                    workerAdded = true;
                }
            } finally {
                mainLock.unlock();
            }
            //（8.5）添加成功则启动任务
            if (workerAdded) {
                t.start();
                workerStarted = true;
            }
        }
    } finally {
        if (! workerStarted)
            addWorkerFailed(w);
    }
    return workerStarted;
}
```



默认的ThreadFactory

```java
// 创建一个默认的ThreadFactory
Executors.defaultThreadFactory()；
/**
 * The default thread factory
 */
static class DefaultThreadFactory implements ThreadFactory {
	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	private final ThreadGroup group;
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	private final String namePrefix;

	DefaultThreadFactory() {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() :
							  Thread.currentThread().getThreadGroup();
		namePrefix = "pool-" +
					  poolNumber.getAndIncrement() +
					 "-thread-";
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r,
							  namePrefix + threadNumber.getAndIncrement(),
							  0);
		if (t.isDaemon())
			t.setDaemon(false);
		if (t.getPriority() != Thread.NORM_PRIORITY)
			t.setPriority(Thread.NORM_PRIORITY);
		return t;
	}
}
```

默认的RejectedExecutionHandler：AbortPolicy

```java
// 默认的Handler
private static final RejectedExecutionHandler defaultHandler = new AbortPolicy();
/**
 * A handler for rejected tasks that throws a
 * {@code RejectedExecutionException}.
 */
public static class AbortPolicy implements RejectedExecutionHandler {
	/**
	 * Creates an {@code AbortPolicy}.
	 */
	public AbortPolicy() { }
	/**
	 * Always throws RejectedExecutionException.
	 *
	 * @param r the runnable task requested to be executed
	 * @param e the executor attempting to execute this task
	 * @throws RejectedExecutionException always
	 */
	public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
		throw new RejectedExecutionException("Task " + r.toString() +
											 " rejected from " +
											 e.toString());
	}
}
```

