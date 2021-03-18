1、Start（）：

启动一个线程，线程处于就绪状态，Java虚拟机会为其创建方法调用栈和程序计数器，并没有执行，一旦分配cpu，开始执行run（）方法

2、interrupt（）

在一个线程中调用另一个线程的interrupt()方法，即会向那个线程发出信号——线程中断状态已被设置。至于那个线程何去何从，由具体的代码实现决定。

```
interrupt（）是给线程设置中断标志；作用于此线程

interrupted（）是检测中断并清除中断状态；作用于此线程

isInterrupted（）只检测中断。作用于当前线程
```

3、join（）

含义：阻塞调用此方法的线程(calling thread)进入 TIMED_WAITING 状态，直到线程t完成，此线程再继续

让“当前线程”等待，而这里的“当前线程”是指当前运行的线程

join方法的本质调用的是Object中的wait方法实现线程的阻塞

4、setDaemon（）

守护线程

5、setPriority（）

6、sleep（）

程序暂停执行指定的时间，让出cpu该其他线程，其监控状态依然保持者，当指定的时间到又会自动恢复运行状态

```java
public static native void sleep(long millis) throws InterruptedException;
// 区别wait（）：
wait()属于Object()，sleep()属于Thread
调用sleep()方法的过程中，线程不会释放对象锁。
调用wait()方法的时候，线程会放弃对象锁，进入等待此对象的等待锁定池，只有针对此对象调用notify()方法后本线程才进入对象锁定池准备获取对象锁进入运行状态。
```

7、yield（）

```java
public static native void yield();
```

8、currentThread（）

```java
// 返回当前正在执行的线程对象Returns a reference to the currently executing thread object
public static native Thread currentThread();
```

9、stop（）

thread.stop()调用之后，创建子线程的线程就会抛出ThreadDeatherror的错误，并且会释放子线程所持有的所有锁，导致数据不一致

10、notify（）、notifyAll（）

notify（）方法只会唤醒一个正在等待的线程(至于唤醒谁，不确定！)

notifyAll（）方法会唤醒所有正在等待的线程。

调用notify和notifyAll方法后，当前线程并不会立即放弃锁的持有权，而必须要等待当前同步代码块执行完才会让出锁。

11、wait（）

调用该方法的线程进入WAITING状态，只有等待另外线程的通知或被中断才会返回，调用wait()方法后，会释放对象的锁。

wait方法一般用在同步方法或同步代码块中