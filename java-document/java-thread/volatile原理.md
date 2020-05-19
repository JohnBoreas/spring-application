1、保证线程可见性

2、防止指令重排

问题：DCL（double check lock）单例需不需要加volatile

<img src="../resource/DCL单例与volatile.png" style="zoom: 50%;" />

4、乱序执行

指令重排





系统底层如何实现数据一致性

1、MESI如果能解决，使用MESI

2、如果不能，就锁总线



系统底层如何保证有序性

1、sfence mfence Ifence等系统原语（内存屏障）

2、锁总线



volatile如何解决指令重排序

1、volatile i

2、ACC_VOLATILE

3、JVM的内存屏障

4、hotspot实现

