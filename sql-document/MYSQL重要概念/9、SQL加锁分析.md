

- [`SELECT ... FROM`](https://dev.mysql.com/doc/refman/5.6/en/select.html)是一致读取，读取数据库的快照并且不设置锁，除非事务隔离级别设置为 [`SERIALIZABLE`](https://dev.mysql.com/doc/refman/5.6/en/innodb-transaction-isolation-levels.html#isolevel_serializable). 对于 [`SERIALIZABLE`](https://dev.mysql.com/doc/refman/5.6/en/innodb-transaction-isolation-levels.html#isolevel_serializable)级别，搜索在它遇到的索引记录上设置共享的下一个键锁。但是，对于使用唯一索引锁定行以搜索唯一行的语句，只需要一个索引记录锁。
- 对于[锁定读取](https://dev.mysql.com/doc/refman/5.6/en/glossary.html#glos_locking_read) （[`SELECT`](https://dev.mysql.com/doc/refman/5.6/en/select.html)with `FOR UPDATE`or `LOCK IN SHARE MODE`）、 [`UPDATE`](https://dev.mysql.com/doc/refman/5.6/en/update.html)和 [`DELETE`](https://dev.mysql.com/doc/refman/5.6/en/delete.html)语句，所采用的锁取决于语句是使用具有唯一搜索条件的唯一索引还是范围类型的搜索条件。
  - 对于具有唯一搜索条件的唯一索引， `InnoDB`只锁定找到的索引记录，而不锁定它之前 的[间隙。](https://dev.mysql.com/doc/refman/5.6/en/glossary.html#glos_gap)
  - 对于其他搜索条件和非唯一索引， `InnoDB`锁定扫描的索引范围，使用[间隙锁](https://dev.mysql.com/doc/refman/5.6/en/glossary.html#glos_gap_lock)或 [下一个键锁](https://dev.mysql.com/doc/refman/5.6/en/glossary.html#glos_next_key_lock) 来阻止其他会话插入该范围所覆盖的间隙。有关间隙锁和下一个键锁的信息，请参阅[第 14.7.1 节，“InnoDB 锁定”](https://dev.mysql.com/doc/refman/5.6/en/innodb-locking.html)。
- 对于搜索遇到的索引记录， [`SELECT ... FOR UPDATE`](https://dev.mysql.com/doc/refman/5.6/en/select.html)阻止其他会话执行 [`SELECT ... LOCK IN SHARE MODE`](https://dev.mysql.com/doc/refman/5.6/en/select.html)或读取某些事务隔离级别。一致读取忽略读取视图中存在的记录上设置的任何锁定。
- [`UPDATE ... WHERE ...`](https://dev.mysql.com/doc/refman/5.6/en/update.html)在搜索遇到的每条记录上设置一个排他的 next-key 锁。但是，对于使用唯一索引锁定行以搜索唯一行的语句，只需要一个索引记录锁。
- 修改聚集索引记录时[`UPDATE`](https://dev.mysql.com/doc/refman/5.6/en/update.html)，会对受影响的二级索引记录进行隐式锁定。[`UPDATE`](https://dev.mysql.com/doc/refman/5.6/en/update.html)在插入新的二级索引记录之前执行重复检查扫描以及插入新的二级索引记录时， 该 操作也会对受影响的二级索引记录进行共享锁。
- [`DELETE FROM ... WHERE ...`](https://dev.mysql.com/doc/refman/5.6/en/delete.html)在搜索遇到的每条记录上设置一个排他的 next-key 锁。但是，对于使用唯一索引锁定行以搜索唯一行的语句，只需要一个索引记录锁。
- [`INSERT`](https://dev.mysql.com/doc/refman/5.6/en/insert.html)在插入的行上设置排他锁。这个锁是索引记录锁，不是next-key锁（即没有间隙锁），并且不会阻止其他会话在插入行之前插入到间隙中。



一、基本的加锁规则

- 常见语句的加锁
  - SELECT ... 语句正常情况下为快照读，不加锁；
  - SELECT ... LOCK IN SHARE MODE 语句为当前读，加 S 锁；
  - SELECT ... FOR UPDATE 语句为当前读，加 X 锁；
  - 常见的 DML 语句（如 INSERT、DELETE、UPDATE）为当前读，加 X 锁；
  - 常见的 DDL 语句（如 ALTER、CREATE 等）加表级锁，且这些语句为隐式提交，不能回滚；
- 表锁
  - 表锁（分 S 锁和 X 锁）
  - 意向锁（分 IS 锁和 IX 锁）
  - 自增锁（一般见不到，只有在 innodb_autoinc_lock_mode = 0 或者 Bulk inserts 时才可能有）
- 行锁
  - 记录锁（分 S 锁和 X 锁）
  - 间隙锁（分 S 锁和 X 锁）
  - Next-key 锁（分 S 锁和 X 锁）
  - 插入意向锁
- 行锁分析
  - 行锁都是加在索引上的，最终都会落在聚簇索引上；
  - 加行锁的过程是一条一条记录加的；
- 锁冲突
  - S 锁和 S 锁兼容，X 锁和 X 锁冲突，X 锁和 S 锁冲突；
  - 表锁和行锁的冲突矩阵参见前面的博客 [了解常见的锁类型](https://www.aneasystone.com/archives/2017/11/solving-dead-locks-two.html)；
- 不同隔离级别下的锁
  - 上面说 SELECT ... 语句正常情况下为快照读，不加锁；但是在 Serializable 隔离级别下为当前读，加 S 锁；
  - RC 隔离级别下没有间隙锁和 Next-key 锁（特殊情况下也会有：purge + unique key）；
  - 不同隔离级别下锁的区别，参见前面的博客 [学习事务与隔离级别](https://www.aneasystone.com/archives/2017/10/solving-dead-locks-one.html)；





INSERT 加锁流程如下（[参考](http://keithlan.github.io/2017/06/21/innodb_locks_algorithms/)）：

- 首先对插入的间隙加插入意向锁（Insert Intension Locks）
  - 如果该间隙已被加上了 GAP 锁或 Next-Key 锁，则加锁失败进入等待；
  - 如果没有，则加锁成功，表示可以插入；
- 然后判断插入记录是否有唯一键，如果有，则进行唯一性约束检查
  - 如果不存在相同键值，则完成插入
  - 如果存在相同键值，则判断该键值是否加锁
    - 如果没有锁， 判断该记录是否被标记为删除
      - 如果标记为删除，说明事务已经提交，还没来得及 purge，这时加 S 锁等待；
      - 如果没有标记删除，则报 1062 duplicate key 错误；
    - 如果有锁，说明该记录正在处理（新增、删除或更新），且事务还未提交，加 S 锁等待；
- 插入记录并对记录加 X 记录锁；

