kafka副本的作用就是提高数据的可靠性，

系统默认副本数量是1，生产环境一般配置数量是2个，保证数据可靠性；否则副本太多会增加磁盘的存储空间，增加网络上的数据传输，降低效率。



kafka的副本分为leader和follower，其中leader数据读写，follower只负责数据同步。

关于副本有下面三个概念：

ISR：表示和leader保持同步的follower集合

OSR：表示follower与leader同步延时过多的副本

AR：分区中所有副本统称为AR（Assigned Repllicas），AR = ISR + OSR，一个分区的AR集合在分配的时候就被指定，并且只要不发生重分配的情况，集合内部副本的顺序是保持不变的，而分区的ISR集合中副本的顺序可能会改变。