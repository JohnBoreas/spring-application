#### 1、线程的基本概念

##### （1）线程的定义：

​		进程中的一个执行任务（控制单元），负责当前进程中程序的执行。一个进程至少有一个线程，一个进程可以运行多个线程，多个线程可共享数据。

​		同一进程的线程共享本进程的地址空间和资源

##### （2）线程状态转化

![线程状态转化](../../resource/%E7%BA%BF%E7%A8%8B%E7%8A%B6%E6%80%81%E8%BD%AC%E5%8C%96.png)

##### （3）线程的分类

守护线程和用户线程

```
用户 (User) 线程：运行在前台，执行具体的任务，如程序的主线程、连接网络的子线程等都是用户线程，daemon = false

守护 (Daemon) 线程：运行在后台，为其他前台线程服务。也可以说守护线程是 JVM 中非守护线程的 “佣人”。一旦所有用户线程都结束运行，守护线程会随 JVM 一起结束工作，daemon = true

main 函数所在的线程就是一个用户线程
```

注意事项：

1、setDaemon(true)必须在start()方法前执行，否则会抛出 IllegalThreadStateException 异常

2、在守护线程中产生的新线程也是守护线程

3、守护 (Daemon) 线程中不能依靠 finally 块的内容来确保执行关闭或清理资源的逻辑。守护线程会随 JVM 一起结束工作，所以守护 (Daemon) 线程中的 finally 语句块可能无法被执行。

**当所有用户线程都执行完，只存在守护线程在运行时，JVM 就退出**。

```java
底层实现
// 当非守护线程数量大于 1 时，就一直等待，直到剩下一个非守护线程时，就会在线程执行完后，退出 JVM。
bool Threads::destroy_vm() {
  JavaThread* thread = JavaThread::current();
#ifdef ASSERT
  _vm_complete = false;
#endif
/**
 * 等待自己是最后一个非守护线程条件
 */
// Wait until we are the last non-daemon thread to execute
{ MonitorLocker nu(Threads_lock);
    while (Threads::number_of_non_daemon_threads() > 1)
    /**
     * 非守护线程数大于 1，则一直等待
     */
    // This wait should make safepoint checks, wait without a timeout,
    // and wait as a suspend-equivalent condition.
    nu.wait(0, Mutex::_as_suspend_equivalent_flag);
}
/**
 * 下面代码是关闭 VM 的逻辑
 */
EventShutdown e;
if (e.should_commit()) {
   e.set_reason("No remaining non-daemon Java threads");
   e.commit();
}
  ...... 省略余下代码
}
```

（4）Java的优先级

Java中线程优先级用1~10来表示，分为三个级别：

```
低优先级：1~4，其中类变量Thread.MIN_PRORITY最低，数值为1；

默认优先级：如果一个线程没有指定优先级，默认优先级为5，由类变量Thread.NORM_PRORITY表示；

高优先级：6~10，类变量Thread.MAX_PRORITY最高，数值为10。
```

注意：

1、不是高优先级的线程就一定先比低优先级执行完，而是高优先级线程先执行的概率比低优先级的线程高。

2、具有相同优先级的多个线程，若它们都为高优先级Thread.MAX_PRORITY，则每个线程都是独占式的，也就是这些线程将被顺序执行；若它们优先级不是高优先级，则这些线程将被同时执行，可以说是无序执行。

