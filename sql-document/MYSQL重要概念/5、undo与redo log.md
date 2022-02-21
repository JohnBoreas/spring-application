##### undo log

就是把所有没有COMMIT的事务回滚到事务开始之前的状态(撤销事务在系统崩溃前可能还没有完成的影响来恢复数据库状态)，对于已经commit的事务不做任何处理。



（1）undo log主要分为两种：

- insert undo log
  代表事务在`insert`新记录时产生的`undo log`, 只在事务回滚时需要，并且在事务提交后可以被立即丢弃
- update undo log
  事务在进行`update`或`delete`时产生的`undo log`; 不仅在事务回滚时需要，在快照读时也需要；所以不能随便删除，只有在快速读或事务回滚不涉及该日志时，对应的日志才会被`purge`线程统一清除

（2）undo log流程

- 在`事务1`修改行数据，先对该行加`排他锁`
- 数据拷贝到`undo log`中，作为旧记录，即副本
- 修改该行`name`值，并修改事务ID，回滚指针指向到`undo log`的副本记录
- 事务提交后，释放锁

![img](..\resource\undo log.png)



undo log必须满足的两条规则：

- U1:如果事务T改变了数据库元素X，那么形如<T,X,v>的日志记录必须在X的新值写到磁盘之前写到磁盘中
- U2:如果事务已提交，则其COMMIT日志记录必须在事务改变的所有数据库元素先写到磁盘之后写到磁盘中，但应尽快。



简要概括规则U1和U2，与一个事务相关额内容必须按如下顺序写到磁盘：

1. 指明所改变数据库元素的日志记录
2. 改变的数据库元素数据本身
3. COMMIT日志记录

**CheckPoint （检查点）**

生成checkpoint的过程为：

1. 记录START_CKPT<T1,T2,...,Tn>，其中Ti表示开始生成checkpoint的时候未决的事务（没有提交的事务）
2. 等待所有事务提交
3. 记录END_CKPT





##### REDO LOG

redo log是指在回放日志的时候把已经commit的事务重做一遍，对于没有commit的事务按照abort处理。

当使用redo日志时，与一个事务相关的材料写到磁盘的顺序为：

1. 指出被修改元素的日志记录
2. 写COMMIT日志
3. 修改数据库元素自身



redo log生成checkpoint的方法：

1. 写日志START_CKPT<T1, T2, ..., Tn>；其中Ti表示当前正在进行还没有commit的事务
2. 当START_CKPT记录写入日志，把所有已提交事务在缓冲区的新值写到磁盘上去。
3. 写日志END_CKPT