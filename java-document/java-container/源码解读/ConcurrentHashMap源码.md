ConcurrentHashMap 

为什么线程安全，采用了什么措施应对高并发 ？分段锁

为什么ConcurrentHashMap不允许插入null值？并发场景下出现歧义

一、实现

 JDK 1.7 

它使用的是数组加链表的形式实现的，而数组又分为：大数组 Segment 和小数组 HashEntry

ConcurrentHashMap 的线程安全是建立在 Segment 加锁的基础上的（分段锁或片段锁）

在 JDK 1.8 中 ConcurrentHashMap 使用的是 CAS + volatile 或 synchronized 的方式来保证线程安全的

<img src="../../resource/JDK 1.7 线程安全实现.png" alt="图片" style="zoom:67%;" />

 JDK 1.8

数组 + 链表/红黑树的方式优化了 ConcurrentHashMap 的实现

链表升级为红黑树的规则：当链表长度大于 8，并且数组的长度大于 64 时，链表就会升级为红黑树的结构。

<img src="../..\resource\JDK 1.8 底层实现.png" alt="图片" style="zoom:67%;" />

二、ConcurrentHashMap 插入null

ConcurrentHashMap 是不能插入 null 值的，否则程序在运行期间就会报空指针异常。

假设 ConcurrentHashMap 允许插入 null，那么此时就会有二义性问题，它的二义性含义有两个：

1. 值没有在集合中，所以返回 null。
2. 值就是 null，所以返回的就是它原本的 null 值。

多线程中，没办法判断某一个时刻返回的 null 值，到底是值为 null，还是压根就不存在

最主要的原因是：**不容忍在并发场景下出现歧义！**