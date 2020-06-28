线程生命周期

```java
/**
  * 线程生命周期中的的六种状态
  * NEW：还没有调用start()的线程实例所处的状态
  * RUNNABLE：正在虚拟机中执行的线程所处的状态
  * BLOCKED：等待在监视器锁上的线程所处的状态
  * WAITING：等待其它线程执行特定操作的线程所处的状态
  * TIMED_WAITING：等待其它线程执行超时操作的线程所处的状态
  * TERMINATED：退出的线程所处的状态
  * 给定时间点，一个线程只会处于以下状态中的一个，这些状态仅仅是虚拟机层面的线程状态，并不能反映任何操作系统中线程的状态
  */
public enum State {
    //还没有调用start()开启的线程实例所处的状态
    NEW, 
    //正在虚拟机中执行或者等待被执行的线程所处的状态，但是这种状态也包含线程正在等待处理器资源这种情况
    RUNNABLE,
    // 等待在监视器锁上的线程所处的状态，比如进入synchronized同步代码块或同步方法失败
    BLOCKED,
    // 等待其它线程执行特定操作的线程所处的状态；比如线程执行了以下方法： Object.wait with no timeout、Thread.join with no timeout、 LockSupport.park
    WAITING,
    // 等待其它线程执行超时操作的线程所处的状态；比如线程执行了以下方法： Thread.sleep、Object.wait with timeout
    //Thread.join with timeout、LockSupport.parkNanos、LockSupport.parkUntil
    TIMED_WAITING,
    //退出的线程所处的状态
    TERMINATED;
}
```

