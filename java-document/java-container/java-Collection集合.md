 Map接口和Collection接口是所有集合框架的父接口：

```
Collection接口的子接口包括：Set接口和List接口
Map接口的实现类主要有：HashMap、TreeMap、Hashtable、ConcurrentHashMap以及Properties等
Set接口的实现类主要有：HashSet、TreeSet、LinkedHashSet等
List接口的实现类主要有：ArrayList、LinkedList、Stack以及Vector等
```

<img src="../resource/collection.jpg" style="zoom:80%;" />

Collection集合主要有List和Set两大接口

```
List：一个有序（元素存入集合的顺序和取出的顺序一致）容器，元素可以重复，可以插入多个null元素，元素都有索引。常用的实现类有 ArrayList、LinkedList 和 Vector。

Set：一个无序（存入和取出顺序有可能不一致）容器，不可以存储重复元素，只允许存入一个null元素，必须保证元素唯一性。Set 接口常用实现类是 HashSet、LinkedHashSet 以及 TreeSet。

Map是一个键值对集合，存储键、值和之间的映射。 Key无序，唯一；value 不要求有序，允许重复。Map没有继承于Collection接口，从Map集合中检索元素时，只要给出键对象，就会返回对应的值对象。
Map 的常用实现类：HashMap、TreeMap、HashTable、LinkedHashMap、ConcurrentHashMap
```



ArrayList 和 LinkedList 的区别是什么？

```
## 数据结构实现：ArrayList 是动态数组的数据结构实现，而 LinkedList 是双向链表的数据结构实现。
## 随机访问效率：ArrayList 比 LinkedList 在随机访问的时候效率要高，因为 LinkedList 是线性的数据存储方式，所以需要移动指针从前往后依次查找。
## 增加和删除效率：在非首尾的增加和删除操作，LinkedList 要比 ArrayList 效率要高，因为 ArrayList 增删操作要影响数组内的其他数据的下标。
## 内存空间占用：LinkedList 比 ArrayList 更占内存，因为 LinkedList 的节点除了存储数据，还存储了两个引用，一个指向前一个元素，一个指向后一个元素。
## 线程安全：ArrayList 和 LinkedList 都是不同步的，也就是不保证线程安全；
```

ArrayList 和 Vector 的区别是什么？

```
这两个类都实现了 List 接口（List 接口继承了 Collection 接口），他们都是有序集合
## 线程安全：Vector 使用了 Synchronized 来实现线程同步，是线程安全的，而 ArrayList 是非线程安全的。
## 性能：ArrayList 在性能方面要优于 Vector。
## 扩容：ArrayList 和 Vector 都会根据实际的需要动态的调整容量，只不过在 Vector 扩容每次会增加 1 倍，而 ArrayList 只会增加 50%。
```

ArrayList 和 HashSet的区别

```
1.HashSet 是不重复的 而且是无序的!
唯一性保证. 重复对象equals方法返回为true ，重复对象hashCode方法返回相同的整数
HashSet其实就是一个HashMap,只是你只能通过Set接口操作这个HashMap的key部分,

2.ArrayList是可重复的 有序的
特点：查询效率高H，增删效率低 轻量级 线程不安全。
arraylist：在数据的插入和删除方面速度不佳，但是在随意提取方面较快
```

HashMap和HashTable的区别是什么？

```
## 线程安全：HashMap是非线程安全的，HashTable是线程安全的；HashTable的方法基本都经过synchronized修饰
## 效率：HashMap比 HashTable效率高一点。
## Null key 和Null value： HashMap的键允许只有一个null，值可多个为null。在 HashTable key为 null，抛NullPointerException。
## 初始容量大小和每次扩充容量大小： 
①创建时不指定容量初始值，Hashtable 默认的初始大小为11，每次扩充，容量变为原来的2n+1。HashMap 默认的初始化大小为16。每次扩充，容量变为原来的2倍。
②创建时如果给定了容量初始值，那么 Hashtable 会直接使用你给定的大小，而 HashMap 会将其扩充为2的幂次方大小。HashMap总是使用2的幂作为哈希表的大小
## 底层数据结构： JDK1.8 以后的 HashMap 在解决哈希冲突时有了较大的变化，当链表长度大于阈值（默认为8）时，将链表转化为红黑树，以减少搜索时间。Hashtable 没有这样的机制。

## 推荐使用：Hashtable 是保留类不建议使用，推荐在单线程环境下使用 HashMap 替代，如果需要多线程使用则用 ConcurrentHashMap 替代。
```

HashMap 和 ConcurrentHashMap 的区别？

```
## 线程安全：
ConcurrentHashMap对整个桶数组进行了分割分段(Segment)，每个分段上都用lock锁进行保护，相对于HashTable的synchronized锁的粒度更精细了一些，并发性能更好，
HashMap没有锁机制，不是线程安全的。（JDK1.8之后ConcurrentHashMap启用了一种全新的方式实现,利用CAS算法。）

## key为null：HashMap的键值对允许有null，但是ConCurrentHashMap都不允许。
```

ConcurrentHashMap 和 Hashtable 的区别？

```
## 底层数据结构： 
JDK1.7的 ConcurrentHashMap 底层采用 分段的数组+链表 实现，
JDK1.8 采用的数据结构跟HashMap1.8的结构一样，数组+链表/红黑二叉树。
Hashtable 和 JDK1.8 之前的 HashMap 的底层数据结构类似都是采用 数组+链表 的形式，数组是 HashMap 的主体，链表则是主要为了解决哈希冲突而存在的；

## 实现线程安全的方式（重要）： 
① 在JDK1.7的时候，ConcurrentHashMap（分段锁）对整个桶数组进行了分割分段(Segment)，每一把锁只锁容器其中一部分数据，多线程访问容器里不同数据段的数据，就不会存在锁竞争，提高并发访问率。（默认分配16个Segment，比Hashtable效率提高16倍。） 
到了JDK1.8的时候已经摒弃了Segment的概念，而是直接用 Node 数组+链表+红黑树的数据结构来实现，并发控制使用 synchronized 和 CAS 来操作。（JDK1.6以后 对 synchronized锁做了很多优化）
② Hashtable(同一把锁) :使用 synchronized 来保证线程安全，效率非常低下。当一个线程访问同步方法时，其他线程也访问同步方法，可能会进入阻塞或轮询状态，如使用 put 添加元素，另一个线程不能使用 put 添加元素，也不能使用 get，竞争会越来越激烈效率越低。
```



|            | 是否线程安全 | 数据结构            | 扩容         | 随机访问     | 有序性 | 重复性 | 查询 | 增删 | 允许null元素 |
| ---------- | ------------ | ------------------- | ------------ | ------------ | ------ | ------ | ---- | ---- | ------------ |
| Arraylist  | 否           | Object[]            | 当前容量*1.5 | O(1)         | 有序   | 可重复 | 快   | 慢   | 允许         |
| Vector     | 是           | Object[]            | 默认一倍     | O(1)         | 有序   | 可重复 | 快   | 慢   |              |
| LinkedList | 否           | Node<E>双向循环链表 |              | 指针移动O(n) | 有序   | 可重复 | 慢   | 快   |              |



|               | 线程安全 | 数据结构                     | 扩容  | 有序性 | 重复性 | 查询 | 允许null元素 |
| ------------- | -------- | ---------------------------- | ----- | ------ | ------ | ---- | ------------ |
| HashMap       | 否       | 数组+链表                    | 2的幂 | 无序   | 不重复 |      | 一个         |
| LinkedHashMap | 否       | 数组和链表或红黑树，双向链表 |       | 有序   | 不重复 |      | 一个         |
| HashTable     | 是       | 数组+链表                    | 2n+1  |        | 不重复 |      | 不允许       |
| TreeMap       | 否       | 红黑树                       |       |        | 不重复 |      |              |



|               | 线程安全 | 数据结构                                   | 有序性 | 重复性 |      |
| ------------- | -------- | ------------------------------------------ | ------ | ------ | ---- |
| HashSet       | 否       | 基于HashMap，数组+链表，value统一为PRESENT | 无序   | 不重复 |      |
| LinkedHashSet |          |                                            |        |        |      |
| TreeSet       |          | 红黑树                                     | 有序   |        |      |

ArrayList 的中间位置插入或者删除元素时，需要对数组进行复制、移动、代价比较高。因此，它适合随机查找和遍历，不适合插入和删除

Vector多线程，比访问 ArrayList 慢

LinkedList 适合数据的动态插入和删除，随机访问和遍历速度比较慢。



**“fail-fast”快速失败机制**

java的错误检测机制，多个线程同时对集合结构操作时会引发快速失败机制

原因：迭代器在遍历访问集合内容时，会使用 modCount 变量，集合在被遍历时内容变化，会改变modCount值，每当迭代器使用hashNext()/next()遍历下一个元素前，都会检测modCount变量是否为expectedmodCount值，是的话就返回遍历；否则抛出异常，终止遍历。

解决办法：

1. 在遍历过程中，所有涉及到改变modCount值得地方全部加上synchronized。
2. 使用CopyOnWriteArrayList来替换ArrayList

**List 遍历的最佳实践**

1、for循环，倒序可进行remove操作

2、Iterator，统一集合遍历接口

3、foreach 循环遍历，不可remove或替换

4、RandomAccess 接口，支持的用 for 循环遍历，否则用 Iterator 或 foreach 遍历（推荐）

**HashMap如何解决哈希冲突的：**

Hash：“散列”，“哈希”，把任意长度的输入通过散列算法，变换成固定长度的输出，输出就是散列值（哈希值）；

​		基本特性：根据同一散列函数计算出的散列值如果不同，那么输入值肯定也不同。但是，根据同一散列函数计算出的散列值如果相同，输入值不一定相同。

哈希冲突：当两个不同的输入值，根据同一散列函数计算出相同的散列值的现象，叫做碰撞（哈希碰撞）。

1、使用链地址法（使用散列表）来链接拥有相同hash值的数据；

2、使用2次扰动函数（hash函数）来降低哈希冲突的概率，使得数据分布更平均；

3、引入红黑树进一步降低遍历的时间复杂度，使得遍历更快；