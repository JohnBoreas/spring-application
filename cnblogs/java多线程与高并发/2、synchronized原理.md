#### 深入理解synchronized

#### 1. synchronized是什么？

java关键字，可以把一个非 NULL 的对象当作锁。是独占式的悲观锁，也是可重 入锁。



#### 2. 使用

1）普通同步方法，锁是当前实例对象。

2）静态同步方法，锁是当前类的Class对象。

3）同步方法块，锁是Synchonized括号里配置的对象。



#### 3.原理

​	JVM规范中，Synchonized的实现原理是，JVM基于进入和退出Monitor对象来实现方法或代码块的同步。



##### 3.1 Monitor是什么？

一种同步工具or一种同步机制，通常被描述为对象监视器。

###### 3.1.1 JVM文档对Monitor的描述

​		同步是围绕一个称为内在锁或监视锁的内部实体构建的。（API规范通常将这个实体简单地称为“监视器”），**每个对象都有一个与之相关联**的内在锁，需要对对象字段进行独占和一致访问的线程必须在访问对象字段之前获取对象的内在锁，然后在访问完对象字段之后释放内在锁。（源于jvm文档）

###### 3.1.2 Monitor的源码结构

```c++
// 结构
ObjectMonitor::ObjectMonitor() {  
  _header       = NULL;  
  _count        = 0;  
  _waiters      = 0,  
  _recursions   = 0;   // 线程的重入次数
  _object       = NULL;  
  _owner        = NULL;// 初始时为NULL。线程占有该monitor时，标记为该线程的唯一标识，通过CAS操作保证
  _WaitSet      = NULL;// 存放调用wait方法而被阻塞的线程的双向循环链表
  _WaitSetLock  = 0 ;  
  _Responsible  = NULL ;  
  _succ         = NULL ;  
  _cxq          = NULL ;// 竞争队列，存放请求锁的线程的后进先出的stack
  FreeNext      = NULL ;  
  _EntryList    = NULL ;// 候选队列，存放_cxq队列中有资格成为候选资源的线程的队列
  _SpinFreq     = 0 ;  
  _SpinClock    = 0 ;  
  OwnerIsThread = 0 ;  
}
```

###### 3.1.3 Monitor相关方法

```c++
1.enter方法：获取锁
2.exit方法：释放锁
3.wait方法：为java的Object的wait方法提供支持
4.notify方法：为java的Object的notify方法提供支持
5.notifyAll方法：为java的Object的notifyAll方法提供支持
```



##### 3.2 具体实现

使用monitorenter和monitorexit指令实现的，JVM要保证每个monitorenter必须有对应的monitorexit与之配对

```
monitorenter：在编译后插入到同步代码块的开始位置

monitorexit：插入到方法结束处和异常处
```



##### 3.3 JAVA对象头

锁信息存放位置，锁上升与之有关

###### 3.3.1 锁信息存放位置：

存放在JAVA对象头中



###### 3.3.1 对象头组成

1）Mark Word（标记字段）：用于存储对象自身的运行时数据，如哈希码（HashCode）、GC分代年龄、锁状态标志、线程持有的锁、偏向线程 ID、偏向时间戳等等，是实现轻量级锁和偏向锁的关键

2）Class Pointer（类型指针）：对象指向它的类元数据的指针，虚拟机通过这个指针来确定这个对象是哪个类的实例;

3）length（数组长度）（数组对象才有）

4）instance data（实例数据）：new 出来的东西

5）padding（对齐）

```c++
// new空一个对象的存储布局：Object object = new Object();
java.lang.Object object internals:
OFFSET SIZE TYPE             DESCRIPTION VALUE
   // markword
   0    4   (object header)  01 00 00 00 (00000001 00000000 00000000 00000000) (1)
   // markword
   4    4   (object header)  00 00 00 00 (00000000 00000000 00000000 00000000) (0)
   // class pointer
   8    4   (object header)  e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
   12   4   (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
```



#### 4. 锁的上升

Java SE 1.6为了减少获得锁和释放锁带来的性能消耗，引入了“偏向锁”和“轻量级锁”：

##### 4.1 锁的四种状态

级别从低到高依次是：无锁状态、偏向锁状态、轻量级锁状态和重量级锁状态

锁可以升级但不能降级

```shell
## 偏向锁
是指一段同步代码一直被一个线程所访问，该线程会自动获取锁。降低获取锁的代价。
识别是不是同一个线程一只获取锁的标志是在上面提到的对象头Mark Word（标记字段）中存储的。
## 轻量级锁
是指当锁是偏向锁的时候，被另一个线程所访问，偏向锁就会升级为轻量级锁，其他线程会通过自旋的形式尝试获取锁，不会阻塞，提高性能。
## 重量级锁
是指当锁为轻量级锁的时候，一个线程自旋到达一定次数的时，还没有获取到锁，就会进入阻塞，该锁膨胀为重量级锁。
重量级锁会让其他申请的线程进入阻塞，性能降低。这时候也就成为了原始的Synchronized的实现。
```

##### 4.2 锁的对比

|    锁    |                             优点                             |                      缺点                      |             适用场景             |
| :------: | :----------------------------------------------------------: | :--------------------------------------------: | :------------------------------: |
|  偏向锁  | 加锁和解锁不需要额外的消耗，和执行非同步方法比仅存在纳秒级的差距 | 如果线程间存在锁竞争，会带来额外的锁撤销的消耗 | 适用于只有一个线程访问同步块场景 |
| 轻量级锁 |           竞争的线程不会阻塞，提高了程序的响应速度           |  如果始终得不到锁竞争的线程使用自旋会消耗CPU   |   追求响应时间,锁占用时间很短    |
| 重量级锁 |               线程竞争不使用自旋，不会消耗CPU                |             线程阻塞，响应时间缓慢             |    追求吞吐量,锁占用时间较长     |



#### 5. 作用

1）确保线程互斥的访问同步代码

2）保证共享变量的修改能够及时可见

3）有效解决重排序问题。



6、Synchonized区别ReentrantLock 

ReentrantLock 有如下特点：

1. 可重入 ReentrantLock 和 syncronized 关键字一样，**都是可重入锁**，不过两者实现原理稍有差别， RetrantLock 利用 **AQS** 的的 state 状态来判断资源是否已锁，同一线程重入加锁， state 的状态 +1 ; 同一线程重入解锁, state 状态 -1 (解锁必须为当前独占线程，否则异常); 当 state 为 0 时解锁成功。
2. **需要手动加锁、解锁** synchronized 关键字是自动进行加锁、解锁的，而 ReentrantLock 需要 lock() 和 unlock() 方法配合 try/finally 语句块来完成，来手动加锁、解锁。
3. 支持**设置锁的超时时间** synchronized 关键字无法设置锁的超时时间，如果一个获得锁的线程内部发生死锁，那么其他线程就会一直进入阻塞状态，而 ReentrantLock **提供 tryLock 方法**，允许设置线程获取锁的超时时间，如果超时，则跳过，不进行任何操作，避免死锁的发生。
4. **支持公平/非公平锁** synchronized 关键字是一种非公平锁，先抢到锁的线程先执行。而 ReentrantLock 的构造方法中允许设置 true/false 来实现公平、非公平锁，如果设置为 true ，则线程获取锁要遵循"先来后到"的规则，每次都会构造一个线程 Node ，然后到双向链表的"尾巴"后面排队，等待前面的 Node 释放锁资源。
5. 可中断锁 ReentrantLock 中的 lockInterruptibly() 方法使得线程**可以在被阻塞时响应中断**，比如一个线程 t1 通过 lockInterruptibly() 方法获取到一个可重入锁，并执行一个长时间的任务，另一个线程通过 interrupt() 方法就可以立刻打断 t1 线程的执行，来获取t1持有的那个可重入锁。而通过 ReentrantLock 的 lock() 方法或者 Synchronized 持有锁的线程是不会响应其他线程的 **interrupt**() 方法的，直到该方法主动释放锁之后才会响应 interrupt() 方法。