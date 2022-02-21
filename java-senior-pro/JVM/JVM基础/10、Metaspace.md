Metaspace主要存储类的元数据，

比如我们加载了一个类，那么这个类的信息就会按照一定的数据结构存储在Metaspace中。

Metaspace的大小和加载类的数目有很大关系，加载的类越多，Metaspace占用内存也就越大。

Metaspace被分配于堆外空间，默认最大空间只受限于系统物理内存。跟它相关的比较重要的两个JVM参数：

```bash
-XX:MetaspaceSize
-XX:MaxMetaspaceSize
```

MaxMetaspaceSize，大家从名字也能猜到是指Metaspace最大值。

而MetaspaceSize可能就比较容易让人误解为是Metaspace的最小值，其实它是指Metaspace扩容时触发FullGC的初始化阈值。

在GC后Metaspace会被动态调整：如果本次GC释放了大量空间，那么就适当降低该值，如果释放的空间较小则适当提高该值，当然它的值不会大于MaxMetaspaceSize.



**Metaspace中的类需要满足什么条件才能够被当成垃圾被卸载回收？**

需同时满足如下三个条件的类才会被卸载：

1. 该类所有的实例都已经被回收；
2. 加载该类的ClassLoader已经被回收；
3. 该类对应的java.lang.Class对象没有任何地方被引用。