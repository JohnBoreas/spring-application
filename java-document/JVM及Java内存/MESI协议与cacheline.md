一、MESI

MESL（缓存一致性）

缓存一致性：在多核CPU中，内存中的数据会在多个核心中存在数据副本，某一个核心发生修改操作，就产生了数据不一致的问题。

一致性协议用于保证多个CPU cache之间缓存共享数据的一致。

<img src="D:/spring-application/java-document/resource/MESI.png" style="zoom:80%;" />

MESI协议将cache line的状态分成modify、exclusive、shared、invalid，分别是修改、独占、共享和失效。

| 状态                     | 描述                                                         | 监听任务                                                     |
| :----------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| M 修改 (Modified)        | 该Cache line有效，数据被修改了，和内存中的数据不一致，数据只存在于本Cache中。 | 缓存行必须时刻监听所有试图读该缓存行相对就主存的操作，这种操作必须在缓存将该缓存行写回主存并将状态变成S（共享）状态之前被延迟执行。 |
| E 独享、互斥 (Exclusive) | 该Cache line有效，数据和内存中的数据一致，数据只存在于本Cache中。 | 缓存行也必须监听其它缓存读主存中该缓存行的操作，一旦有这种操作，该缓存行需要变成S（共享）状态。 |
| S 共享 (Shared)          | 该Cache line有效，数据和内存中的数据一致，数据存在于很多Cache中。 | 缓存行也必须监听其它缓存使该缓存行无效或者独享该缓存行的请求，并将该缓存行变成无效（Invalid）。 |
| I 无效 (Invalid)         | 该Cache line无效。                                           | 无                                                           |



二、cache

原因：CPU的频率太快了，快到主存跟不上，在处理器时钟周期内，CPU常常需要等待主存，浪费资源。

目的：是为了缓解CPU和内存之间速度的不匹配问题（结构：cpu -> cache -> memory）

```
局部性原理。
  A. 时间局部性：如果某个数据被访问，那么在不久的将来它很可能被再次访问；
  B. 空间局部性：如果某个数据被访问，那么与它相邻的数据很快也可能被访问；
```

1、cache与寄存器关系：

寄存器存放的是当前CPU执行的数据，而cache则缓存与该数据相关的部分数据

2、多核CPU cache结构

L1分成了指令（L1P）和数据（L1D）两部分，而L2则是指令和数据共存，

L1和L2是CPU私有的，L3则是所有CPU核心共享的。

<img src="D:/spring-application/java-document/resource/多核CPU-cache结构.png" alt="多核CPU-cache结构.png" style="zoom:75%;" />

3、cache消耗的时间（单位：时钟周期）

<img src="D:/spring-application/java-document/resource/java-cache-cost-time.png" style="zoom:67%;" />

4、cache的基本结构原理图

<img src="D:/spring-application/java-document/resource/cache基本结构原理框图.png" style="zoom:80%;" />

三、cache line缓存行概念

cache line是cache与内存数据交换的最小单位，根据操作系统一般是32byte或64byte

在MESI协议中，状态可以是M、E、S、I，地址则是cache line中映射的内存地址，数据则是从内存中读取的数据。

<img src="D:/spring-application/java-document/resource/cache-line结构.png" style="zoom:75%;" />

1、工作方式：当CPU从cache中读取数据的时候，会比较地址是否相同，如果相同则检查cache line的状态，再决定该数据是否有效，无效则从主存中获取数据，或者根据一致性协议发生一次cache-to--chache的数据推送

2、工作效率：当CPU能够从cache中拿到有效数据的时候，消耗几个CPU cycle，如果发生cache miss，则会消耗几十上百个CPU cycle；



cpu在读数据时候是以块为单位读，

volatile也是对数据以块来处理，64字节为一缓存行，以行为单位

<img src="D:/spring-application/java-document/resource/cacheline.png" style="zoom:80%;" />

四、操作系统

（1）计算机组成

<img src="D:/spring-application/java-document/resource/计算机组成.png" style="zoom:80%;" />

（2）存储结构

<img src="D:/spring-application/java-document/resource/存储器的层次结构.png" style="zoom:60%;" />





CPU内部的cache种类, 至少有三种
1) 指令cache
2) 数据cache 通常有多级 multi-level
3) TLB 加速虚拟地址2物理地址转换



cache entry (cache条目)
包含如下部分
1) cache line : 从主存一次copy的数据大小)
2) tag : 标记cache line对应的主存的地址
3) falg : 标记当前cache line是否invalid, 如果是数据cache, 还有是否dirty



cpu访问主存的规律
1) cpu从来都不直接访问主存, 都是通过cache间接访问主存
2) 每次需要访问主存时, 遍历一遍全部cache line, 查找主存的地址是否在某个cache line中.
3) 如果cache中没有找到, 则分配一个新的cache entry, 把主存的内存copy到cache line中, 再从cache line中读取.



cache中包含的cache entry条目有限, 所以, 必须有合适的cache淘汰策略
一般使用的是LRU策略.
将一些主存区域标记为non-cacheble, 可以提高cache命中率, 降低没用的cache



回写策略
cache中的数据更新后,需要回写到主存, 回写的时机有多种
1) 每次更新都回写. write-through cache
2) 更新后不回写,标记为dirty, 仅当cache entry被evict时才回写
3) 更新后, 把cache entry送如回写队列, 待队列收集到多个entry时批量回写.



cache一致性问题
有两种情况可能导致cache中的数据过期
1) DMA, 有其他设备直接更新主存的数据
2) SMP, 同一个cache line存在多个CPU各自的cache中. 其中一个CPU对其进行了更新.



cpu stall cpu失速
指的是当cache miss时(特别是read cache miss), cpu在等待数据从内存读进去cache中期间, 没事可做.
解决此问题的方法有
1) 超线程技术. CPU在硬件层面, 把一个CPU模拟成两个CPU, 在上层看来是两个CPU. 并发的执行两个线程. 这样当一个线程因cache miss在等待时, 另一个线程可以执行.