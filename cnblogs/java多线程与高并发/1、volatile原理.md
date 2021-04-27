#### 														volatile原理

#### 1.volatile是什么

volatile是java虚拟机提供的轻量级同步机制，不会引起线程上下文的切换和调度。

#### 2.volatile三个比较重要的点

```
1、保证线程可见性

2、不保证原子性

3、防止指令重排
```

##### 2.1.可见性

###### 2.1.1.什么是可见性？

指当一个线程修改一个共享变量时，另外一个线程能读到这个修改的值

###### 2.1.2.如何保证可见性？

volatile修饰的共享变量做写操作时会多出一行汇编代码----Lock前缀的指令，并多核处理器下会引发了两层语义：

```
0x01a3de1d: movb $0×0,0×1104800(%esi);
0x01a3de24: lock addl $0×0,(%esp);
```

1）Lock前缀指令会引起处理器缓存回写到内存。

​		缓存一致性机制会阻止同时修改由两个以上处理器缓存的内存区域数据

2）一个处理器的缓存回写到内存会导致其他处理器的缓存无效。

​		MESI（修改、独占、共享、无效）控制协议去维护内部缓存和其他处理器缓存的一致性



##### 2.2.原子性

###### 2.2.1.什么是原子性？

不可分割的，完整性，一连串操作要么都成功要么都失败，不被中断

只能保证对单次读/写的原子性，保证原子性需要通过加锁的方式去解决



##### 2.3.指令重排

###### 2.3.1.什么是指令重排？

指令重排序是为了优化指令，提高程序运行效率。包括编译器重排序和运行时重排序

###### 2.3.2.作用

保证了有序性

###### 2.3.3.如何防止指令重排？

为实现volatile内存语义，编译器生成字节码时，会在指令序列中插入内存屏障来禁止特定类型的处理器重排序。

```java
// JVM的内存屏障：屏障两边的指令不能重排
StoreStoreBarrier	  LoadLoadBarrier
volatile写操作			volatile读操作
StoreLoadBarrier	  LoadStoreBarrier
```



#### 3.volatile内存语义

* 写的内存语义：当写一个volatile变量时，JMM会把该线程对应的本地内存中的共享变量值刷新到主内存。

* 读的内存语义：当读一个volatile变量时，JMM会把该线程对应的本地内存置为无效。线程接下来将从主内存中读取共享变量。

##### 3.1.内存语义的实现

​		为了实现volatile的内存语义，JMM 会分别限制编译器重排序和处理器重排序两种类型的重排序类型，通过编译器在生成字节码时，在指令序列中插入内存屏障来禁止特定类型的处理器重排序。



#### 4. DCL（double check lock）

单例需不需要加volatile

```java
## 双重检查（double-checked）
class Singleton {
    private volatile static Singleton instance;
    public static Singleton getInstance() {
        if (instance == null) {
            syschronized(Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    } 
}
```

