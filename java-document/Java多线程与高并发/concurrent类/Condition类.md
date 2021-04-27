Condition接口提供了类似Object的监视器方法，与Lock配合可以实现等待/通知模式

**Condition与Object对比：**

<img src="../../resource/Condition接口.png" style="zoom:70%;" />

Condition是AQS的内部类。每个Condition对象都包含一个队列(等待队列)。

等待队列是一FIFO的队列，队列每个节点都包含了一个线程引用，该线程就是在Condition对象上等待的线程，

如果一个线程调用了Condition.await()方法，那么该线程将会释放锁、构造成节点加入等待队列并进入等待状态。

示例：

```java
// 线程1
public void conditionWait() {
    lock.lock();
    try {
        System.out.println(Thread.currentThread().getName() + "拿到锁了");
        System.out.println(Thread.currentThread().getName() + "等待信号");
        // await()方法后，当前线程会释放锁并在此等待
        condition.await();
        System.out.println(Thread.currentThread().getName() + "拿到信号");
    } catch (Exception e) {

    } finally {
        lock.unlock();
    }
}
// 线程2
public void conditionSignal() {
    lock.lock();
    try {
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getName() + "拿到锁了");
        // 其他线程signal()方法，通知当前线程后，当前线程才从await()方法返回，并且在返回前已经获取了锁
        condition.signal();
        System.out.println(Thread.currentThread().getName() + "发出信号");
    } catch (Exception e) {

    } finally {
        lock.unlock();
    }
}
```





```java
public final void await() throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
    Node node = addConditionWaiter();
    int savedState = fullyRelease(node);
    int interruptMode = 0;
    while (!isOnSyncQueue(node)) {
        LockSupport.park(this);
        if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
            break;
    }
    if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
        interruptMode = REINTERRUPT;
    if (node.nextWaiter != null) // clean up if cancelled
        unlinkCancelledWaiters();
    if (interruptMode != 0)
        reportInterruptAfterWait(interruptMode);
}
```