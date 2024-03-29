##### 上下文切换

（1）一次上下文切换

任务从保存到再加载的过程

CPU通过时间片分配算法来循环执行任务，当前任务执行一个时间片后会切换到下一个
任务，切换前会保存上一个任务的状态。

（2）时间片一般是几十毫秒（ms）。

（3）测试上下文切换次数和时长

```shell
## 测量上下文切换的次数
vmstat 1
## 测量上下文切换的时长
Lmbench3（性能分析工具）
```

（4）如何减少上下文切换

无锁并发编程、CAS算法、使用最少线程和使用协程。

```shell
## 无锁并发编程
多线程竞争锁时，会引起上下文切换，所以多线程处理数据时，可以用一些办法来避免使用锁，如将数据的ID按照Hash算法取模分段，不同的线程处理不同段的数据。
## CAS算法
Java的Atomic包使用CAS算法来更新数据，而不需要加锁。
## 使用最少线程
避免创建不需要的线程，比如任务很少，但是创建了很多线程来处理，这样会造成大量线程都处于等待状态。
## 协程
在单线程里实现多任务的调度，并在单线程里维持多个任务间的切换。
```

线程每一次从WAITTING到RUNNABLE都会进行一次上下文的切换。





##### 死锁

（1）避免死锁的常见方法。

```shell
·避免一个线程同时获取多个锁。

·避免一个线程在锁内同时占用多个资源，尽量保证每个锁只占用一个资源。

·尝试使用定时锁，使用lock.tryLock（timeout）来替代使用内部锁机制。

·对于数据库锁，加锁和解锁必须在一个数据库连接里，否则会出现解锁失败的情况。
```



##### 资源限制的挑战

（1）什么是资源限制

资源限制是指在进行并发编程时，程序的执行速度受限于计算机硬件资源或软件资源。

（2）资源限制引发的问题

串行的代码受限于资源并发执行，仍然在串行执行，程序不仅不会加快执行，反而会更慢，因为增加了上下文切换和资源调度的时间。

（3）如何解决资源限制的问题

对于硬件资源限制，可以考虑使用集群并行执行程序。单机的资源有限制，就让程序在多机上运行。

比如使用ODPS、Hadoop或者自己搭建服务器集群，不同的机器处理不同的数据。可以通过“数据ID%机器数”，计算得到一个机器编号，然后由对应编号的机器处理这笔数据。对于软件资源限制，可以考虑使用资源池将资源复用。比如使用连接池将数据库和Socket连接复用，或者在调用对方webservice接口获取数据时，只建立一个连接。

（4）在资源限制情况下进行并发编程

如何在资源限制的情况下，让程序执行得更快呢？

根据不同的资源限制调整程序的并发度，比如下载文件程序依赖于两个资源——带宽和硬盘读写速度。有数据库操作时，涉及数据库连接数，如果SQL语句执行非常快，而线程的数量比数据库连接数大很多，则某些线程会被阻塞，等待数据库连接。