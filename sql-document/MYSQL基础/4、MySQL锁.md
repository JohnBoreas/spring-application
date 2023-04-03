一、锁分类

- [共享锁和独占锁](https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html#innodb-shared-exclusive-locks)
- [意向锁](https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html#innodb-intention-locks)
- [记录锁](https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html#innodb-record-locks)
- [间隙锁](https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html#innodb-gap-locks)
- [下一键锁](https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html#innodb-next-key-locks)
- [插入意向锁](https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html#innodb-insert-intention-locks)
- [AUTO-INC 锁](https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html#innodb-auto-inc-locks)
- [空间索引的谓词锁](https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html#innodb-predicate-locks)



mysql锁分成两类：锁类型（lock_type）和锁模式（lock_mode）

（1）锁模式

- LOCK_IS：读意向锁；
- LOCK_IX：写意向锁；
- LOCK_S：读锁；
- LOCK_X：写锁；
- LOCK_AUTO_INC：自增锁；

（2）锁类型

表锁和行锁



二、mysql锁进行划分：

1、按照锁的粒度划分：行锁、表锁、页锁

2、按照锁的使用方式划分：共享锁、排它锁（悲观锁的一种实现）

- [共享 ( `S`) 锁](https://dev.mysql.com/doc/refman/8.0/en/glossary.html#glos_shared_lock)允许持有锁的事务读取一行 。
- [独占 ( `X`) 锁](https://dev.mysql.com/doc/refman/8.0/en/glossary.html#glos_exclusive_lock)允许持有锁的事务更新或删除行 。

3、还有两种思想上的锁：悲观锁、乐观锁。

4、InnoDB中有几种行级锁类型：Record Lock、Gap Lock、Next-key Lock

​		Record Lock：在索引记录上加锁

​		Gap Lock：间隙锁

​		Next-key Lock：Record Lock+Gap Lock

5、意向锁

*多粒度锁*，允许行锁和表锁并存

- [意向共享锁](https://dev.mysql.com/doc/refman/8.0/en/glossary.html#glos_intention_shared_lock)( `IS`) 表示事务打算在表中的各个行上设置 共享 锁 *。*

- [意向排他锁](https://dev.mysql.com/doc/refman/8.0/en/glossary.html#glos_intention_exclusive_lock) ( )`IX`表示事务打算在表中的各个行上设置排他锁。

  如：[`SELECT ... FOR SHARE`](https://dev.mysql.com/doc/refman/8.0/en/select.html)设置一个`IS`锁，并 [`SELECT ... FOR UPDATE`](https://dev.mysql.com/doc/refman/8.0/en/select.html)设置一个`IX`锁。

  意向锁定协议如下：

  - 在事务可以获取表中行的共享锁之前，它必须首先获取`IS`表上的锁或更强锁。
  - 在事务可以获取表中一行的排他锁之前，它必须首先获取`IX` 表上的锁。

![数据库锁分类思维导图](..\resource\mysql锁.jpg)



##### InnoDB 存储引擎的锁算法

- Record Lock — 单个行记录上的锁；
- Gap Lock — 间隙锁，锁定一个范围，不包括记录本身；
- Next-Key Lock — 锁定一个范围，包括记录本身。



##### 共享锁

共享锁又称读锁 (read lock)，是读取操作创建的锁。

其他用户可以并发读取数据，但任何事务都不能对数据进行修改（获取数据上的排他锁），直到已释放所有共享锁。当如果事务对读锁进行修改操作，很可能会造成死锁。



##### 排它锁

排他锁 exclusive lock（也叫 writer lock）又称写锁。

若某个事物对某一行加上了排他锁，只能这个事务对其进行读写，在此事务结束之前，其他事务不能对其进行加任何锁，其他进程可以读取,不能进行写操作，需等待其释放。

排它锁是悲观锁的一种实现，在上面悲观锁也介绍过。

若事务 1 对数据对象 A 加上 X 锁，事务 1 可以读 A 也可以修改 A，其他事务不能再对 A 加任何锁，直到事物 1 释放 A 上的锁。这保证了其他事务在事物 1 释放 A 上的锁之前不能再读取和修改 A。排它锁会阻塞所有的排它锁和共享锁。



##### 意向锁(Intention Locks)

​		如果请求事务与现有锁兼容，则将锁授予请求事务，但如果它与现有锁冲突，则不会授予该锁。事务等待，直到释放冲突的现有锁。[如果锁定请求与现有锁定冲突并且由于会导致死锁](https://dev.mysql.com/doc/refman/8.0/en/glossary.html#glos_deadlock)而无法授予 ，则会发生错误。

​		意向锁不会阻塞除全表请求（例如，[`LOCK TABLES ... WRITE`](https://dev.mysql.com/doc/refman/8.0/en/lock-tables.html)）之外的任何内容。意向锁的主要目的是表明有人正在锁定一行，或者将要锁定表中的一行。

​		[意向锁的事务数据在InnoDB 监视器](https://dev.mysql.com/doc/refman/8.0/en/innodb-standard-monitor.html) 输出 [`SHOW ENGINE INNODB STATUS`](https://dev.mysql.com/doc/refman/8.0/en/show-engine.html)中 类似于以下内容：

```sql
TABLE LOCK table `test`.`t` trx id 10080 lock mode IX
```

- 意向共享锁( IS 锁)：事务想要获得一张表中某几行的共享锁
- 意向排他锁( IX 锁)： 事务想要获得一张表中某几行的排他锁

共享锁、排他锁与意向锁的兼容矩阵如下：

|              | 排他锁X | 意向排他锁IX | 共享锁S | 意向共享锁IS |
| :----------: | :-----: | :----------: | :-----: | :----------: |
|   排他锁X    | `冲突`  |    `冲突`    | `冲突`  |    `冲突`    |
| 意向排他锁IX | `冲突`  |     兼容     | `冲突`  |     兼容     |
|   共享锁S    | `冲突`  |    `冲突`    |  兼容   |     兼容     |
| 意向共享锁IS | `冲突`  |     兼容     |  兼容   |     兼容     |



##### InnoDB 如何实现行锁？

行级锁是 MySQL 中粒度最小的一种锁，他能大大减少数据库操作的冲突。

INNODB 的行级锁有共享锁（S LOCK）和排他锁（X LOCK）两种。共享锁允许事物读一行记录，不允许任何线程对该行记录进行修改。排他锁允许当前事物删除或更新一行记录，其他线程不能操作该记录。

共享锁：SELECT ... LOCK IN SHARE MODE，MySQL 会对查询结果集中每行都添加共享锁，前提是当前线程没有对该结果集中的任何行使用排他锁，否则申请会阻塞。

排他锁：select * from t where id=1 for update，其中 id 字段必须有索引，MySQL 会对查询结果集中每行都添加排他锁，在事物操作中，任何对记录的更新与删除操作会自动加上排他锁。前提是当前没有线程对该结果集中的任何行使用排他锁或共享锁，否则申请会阻塞。