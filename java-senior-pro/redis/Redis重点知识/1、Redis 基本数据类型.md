1. 字符串：简单动态字符串 SDS 的抽象类型。
2. 链表 linkedlist：redis 链表是一个双向无环链表结构，
3. 字典 hashtable：每个字典带有两个 hash 表，
4. 跳跃表 skiplist：redis 跳跃表由 zskiplist 和 zskiplistNode 组成
5. 整数集合 intset：用于保存整数值的集合抽象数据结构，不会出现重复元素，底层实现为数组。
6. 压缩列表 ziplist：压缩列表是为节约内存而开发的顺序性数据结构，他可以包含多个节点，每个节点可以保存一个字节数组或者整数值。



不同对象的编码：

1. 字符串对象 string：int 整数、embstr 编码的简单动态字符串、raw 简单动态字符串
2. 列表对象 list：ziplist、linkedlist
3. 哈希对象 hash：ziplist、hashtable
4. 集合对象 set：intset、hashtable
5. 有序集合对象 zset：ziplist、skiplist

