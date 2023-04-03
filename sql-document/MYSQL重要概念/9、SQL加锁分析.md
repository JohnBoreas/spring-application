
- [`SELECT ... FROM`](https://dev.mysql.com/doc/refman/5.6/en/select.html)是一致读取，**读取数据库的快照并且不设置锁**，除非事务隔离级别设置为 [`SERIALIZABLE`](https://dev.mysql.com/doc/refman/5.6/en/innodb-transaction-isolation-levels.html#isolevel_serializable). 对于 [`SERIALIZABLE`](https://dev.mysql.com/doc/refman/5.6/en/innodb-transaction-isolation-levels.html#isolevel_serializable)级别，搜索在它遇到的索引记录上设置共享的下一个键锁。但是，对于使用唯一索引锁定行以搜索唯一行的语句，只需要一个索引记录锁。

- [`SELECT ... FOR UPDATE`](https://dev.mysql.com/doc/refman/8.0/en/select.html)和 [`SELECT ... FOR SHARE`](https://dev.mysql.com/doc/refman/8.0/en/select.html)使用唯一索引的语句获取扫描行的锁，并释放不符合包含在结果集中的行的锁（例如，如果它们不符合 `WHERE`子句中给出的条件）。但是，在某些情况下，行可能不会立即解锁，因为结果行与其原始源之间的关系在查询执行期间丢失了。例如，在一个 [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html)，在评估它们是否符合结果集之前，可能会将来自表的扫描（和锁定）行插入到临时表中。在这种情况下，临时表中的行与原始表中的行的关系丢失，并且后面的行直到查询执行结束才解锁。

- 对于[锁定读取](https://dev.mysql.com/doc/refman/5.6/en/glossary.html#glos_locking_read) （[`SELECT`](https://dev.mysql.com/doc/refman/5.6/en/select.html)with `FOR UPDATE`or `LOCK IN SHARE MODE`）、 [`UPDATE`](https://dev.mysql.com/doc/refman/5.6/en/update.html)和 [`DELETE`](https://dev.mysql.com/doc/refman/5.6/en/delete.html)语句，所采用的锁取决于语句是使用具有唯一搜索条件的唯一索引还是范围类型的搜索条件。
  - 对于具有唯一搜索条件的唯一索引， `InnoDB`只锁定找到的索引记录，而不锁定它之前 的[间隙。](https://dev.mysql.com/doc/refman/5.6/en/glossary.html#glos_gap)
  - 对于其他搜索条件和非唯一索引， `InnoDB`锁定扫描的索引范围，使用[间隙锁](https://dev.mysql.com/doc/refman/5.6/en/glossary.html#glos_gap_lock)或 [下一个键锁](https://dev.mysql.com/doc/refman/5.6/en/glossary.html#glos_next_key_lock) 来阻止其他会话插入该范围所覆盖的间隙。有关间隙锁和下一个键锁的信息，请参阅[第 14.7.1 节，“InnoDB 锁定”](https://dev.mysql.com/doc/refman/5.6/en/innodb-locking.html)。
  
- 对于搜索遇到的索引记录， [`SELECT ... FOR UPDATE`](https://dev.mysql.com/doc/refman/5.6/en/select.html)阻止其他会话执行 [`SELECT ... LOCK IN SHARE MODE`](https://dev.mysql.com/doc/refman/5.6/en/select.html)或读取某些事务隔离级别。一致读取忽略读取视图中存在的记录上设置的任何锁定。

- [`UPDATE ... WHERE ...`](https://dev.mysql.com/doc/refman/5.6/en/update.html)在搜索遇到的每条记录上设置一个**排他的 next-key 锁**。但是，对于使用唯一索引锁定行以搜索唯一行的语句，只需要一个**索引记录锁**。

- 修改聚集索引记录时[`UPDATE`](https://dev.mysql.com/doc/refman/5.6/en/update.html)，会对受影响的二级索引记录进行隐式锁定。[`UPDATE`](https://dev.mysql.com/doc/refman/5.6/en/update.html)在插入新的二级索引记录之前执行重复检查扫描以及插入新的二级索引记录时， 该 操作也会对受影响的二级索引记录进行共享锁。

- [`DELETE FROM ... WHERE ...`](https://dev.mysql.com/doc/refman/5.6/en/delete.html)在搜索遇到的每条记录上设置一个排他的 **next-key 锁**。但是，对于使用唯一索引锁定行以搜索唯一行的语句，只需要一个**索引记录锁**。

- [`INSERT`](https://dev.mysql.com/doc/refman/8.0/en/insert.html)在插入的行上设置排他锁。这个锁是**index-record lock，**不是next-key lock（即没有gap lock），不会阻止其他session插入插入行之前的gap。

  在插入行之前，设置一种称为**插入意图间隙锁的间隙锁**。这个锁表示插入的意图，这样插入到同一个索引间隙中的多个事务如果没有插入到间隙中的相同位置，则不需要相互等待。假设有值为 4 和 7 的索引记录。尝试插入值 5 和 6 的单独事务在获得对插入行的排他锁之前分别使用插入意向锁锁定 4 和 7 之间的间隙，但不相互阻塞，因为行不冲突。
  
  **如果发生duplicate-key错误，则会在重复索引记录上设置共享锁。如果另一个会话已经具有独占锁，那么如果有多个会话试图插入同一行，那么使用共享锁可能会导致死锁。如果另一个会话删除该行，就会发生这种情况。**

- [`INSERT ... ON DUPLICATE KEY UPDATE`](https://dev.mysql.com/doc/refman/8.0/en/insert-on-duplicate.html)与 simple [`INSERT`](https://dev.mysql.com/doc/refman/8.0/en/insert.html)的不同之处在于，当发生重复键错误时，将排他锁而不是共享锁放在要更新的行上。对重复的主键值采用**独占索引记录锁**。对重复的唯一键值采用独占下一键锁。

- [`REPLACE`](https://dev.mysql.com/doc/refman/8.0/en/replace.html)就像 [`INSERT`](https://dev.mysql.com/doc/refman/8.0/en/insert.html)在唯一键上没有碰撞一样完成。否则，将排他的下一键锁放置在要替换的行上。

- `INSERT INTO T SELECT ... FROM S WHERE ...` 在插入到的每一行上设置排他索引记录锁（没有间隙锁）`T`。如果事务隔离级别为[`READ COMMITTED`](https://dev.mysql.com/doc/refman/8.0/en/innodb-transaction-isolation-levels.html#isolevel_read-committed)，`InnoDB`则将搜索`S`作为一致读取（无锁）进行。否则，`InnoDB`对来自 的行设置共享的下一键锁`S`。 `InnoDB`在后一种情况下必须设置锁：在使用基于语句的二进制日志进行前滚恢复期间，每个 SQL 语句都必须以与最初相同的方式执行。

  [`CREATE TABLE ... SELECT ...`](https://dev.mysql.com/doc/refman/8.0/en/create-table.html)使用共享的下一键锁或作为一致读取执行 [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html)，至于 [`INSERT ... SELECT`](https://dev.mysql.com/doc/refman/8.0/en/insert-select.html).

  当`SELECT`在结构 `REPLACE INTO t SELECT ... FROM s WHERE ...` or中使用 a 时`UPDATE t ... WHERE col IN (SELECT ... FROM s ...)`，`InnoDB`对表中的行设置共享的下一键锁`s`。

- `InnoDB``AUTO_INCREMENT`在初始化表上先前指定的列时，在与列关联的索引的末尾设置排他锁 `AUTO_INCREMENT`。

  使用 [`innodb_autoinc_lock_mode=0`](https://dev.mysql.com/doc/refman/8.0/en/innodb-parameters.html#sysvar_innodb_autoinc_lock_mode), `InnoDB`使用特殊的 `AUTO-INC`表锁模式，在访问自增计数器时获取锁并保持到当前 SQL 语句的末尾（而不是整个事务的末尾）。`AUTO-INC`当持有表锁时，其他客户端无法插入到表中。相同的行为发生在“批量插入” 中 [`innodb_autoinc_lock_mode=1`](https://dev.mysql.com/doc/refman/8.0/en/innodb-parameters.html#sysvar_innodb_autoinc_lock_mode)。表级`AUTO-INC`锁不与 一起使用 [`innodb_autoinc_lock_mode=2`](https://dev.mysql.com/doc/refman/8.0/en/innodb-parameters.html#sysvar_innodb_autoinc_lock_mode)。有关更多信息，请参阅 [第 15.6.1.6 节，“InnoDB 中的 AUTO_INCREMENT 处理”](https://dev.mysql.com/doc/refman/8.0/en/innodb-auto-increment-handling.html)。

  `InnoDB``AUTO_INCREMENT`在不设置任何锁 的情况下获取先前初始化的列的值。

- 如果`FOREIGN KEY`在表上定义了约束，则任何需要检查约束条件的插入、更新或删除都会在它查看以检查约束的记录上设置共享记录级锁。 `InnoDB`在约束失败的情况下也会设置这些锁。

- [`LOCK TABLES`](https://dev.mysql.com/doc/refman/8.0/en/lock-tables.html)`InnoDB`设置表锁，但它是设置这些锁 的层之上的更高 MySQL 层 。`InnoDB`如果 `innodb_table_locks = 1`（默认）和 知道表锁[`autocommit = 0`](https://dev.mysql.com/doc/refman/8.0/en/server-system-variables.html#sysvar_autocommit)，并且上面的 MySQL 层`InnoDB`知道行级锁。

  否则，`InnoDB`的自动死锁检测无法检测到涉及此类表锁的死锁。此外，因为在这种情况下，较高的 MySQL 层不知道行级锁，所以有可能在另一个会话当前具有行级锁的表上获得表锁。但是，这不会危及事务完整性，如 [第 15.7.5.2 节“死锁检测”](https://dev.mysql.com/doc/refman/8.0/en/innodb-deadlock-detection.html)中所述。

- [`LOCK TABLES`](https://dev.mysql.com/doc/refman/8.0/en/lock-tables.html)`innodb_table_locks=1`如果（默认），则在每个表上获取两个锁。除了MySQL层的表锁，它还获取了`InnoDB`表锁。为避免获取`InnoDB`表锁，请设置 `innodb_table_locks=0`. 如果没有 `InnoDB`获取表锁， [`LOCK TABLES`](https://dev.mysql.com/doc/refman/8.0/en/lock-tables.html)即使表的某些记录被其他事务锁定，也会完成。

  在 MySQL 8.0 中， [`innodb_table_locks=0`](https://dev.mysql.com/doc/refman/8.0/en/innodb-parameters.html#sysvar_innodb_table_locks)对使用 显式锁定的表没有影响 [`LOCK TABLES ... WRITE`](https://dev.mysql.com/doc/refman/8.0/en/lock-tables.html)。[`LOCK TABLES ... WRITE`](https://dev.mysql.com/doc/refman/8.0/en/lock-tables.html)它确实对通过隐式（例如，通过触发器）或通过锁定以供读取或写入的表有影响 [`LOCK TABLES ... READ`](https://dev.mysql.com/doc/refman/8.0/en/lock-tables.html)。

- `InnoDB`当事务提交或中止时，事务持有的 所有锁都会被释放。因此，在mode中调用表 没有多大意义， [`LOCK TABLES`](https://dev.mysql.com/doc/refman/8.0/en/lock-tables.html)因为 获取的表锁将立即释放。 `InnoDB`[`autocommit=1`](https://dev.mysql.com/doc/refman/8.0/en/server-system-variables.html#sysvar_autocommit)`InnoDB`




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
    - LOCK_ORDINARY：也称为 **Next-Key Lock**，锁一条记录及其之前的间隙，这是 RR 隔离级别用的最多的锁，从名字也能看出来；
    - LOCK_GAP：间隙锁，锁两个记录之间的 GAP，防止记录插入；
    - LOCK_REC_NOT_GAP：只锁记录；
    - LOCK_INSERT_INTENSION：插入意向 GAP 锁，插入记录时使用，是 LOCK_GAP 的一种特例。
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



