死锁种类：

1、insert ... on duplicate key 导致

2、批量插入修改数据，多进程，或者同批次存在同一数据

3、一个事务修改多个表



mysql文档对死锁的一个处理：

`InnoDB`使用自动行级锁定。即使在仅插入或删除单行的事务的情况下，您也可能会出现死锁。那是因为这些操作并不是真正的“原子”；它们会自动对插入或删除的行的（可能是多个）索引记录设置锁定。



使用以下技术应对死锁并降低其发生的可能性：

- 随时发出问题 [`SHOW ENGINE INNODB STATUS`](https://dev.mysql.com/doc/refman/5.6/en/show-engine.html)以确定最近死锁的原因。这可以帮助您调整应用程序以避免死锁。

- [`innodb_print_all_deadlocks`](https://dev.mysql.com/doc/refman/5.6/en/innodb-parameters.html#sysvar_innodb_print_all_deadlocks) 如果频繁的死锁警告引起关注，请通过启用该变量 来收集更广泛的调试信息 。有关每个死锁的信息，而不仅仅是最新的，都记录在 MySQL [错误日志](https://dev.mysql.com/doc/refman/5.6/en/glossary.html#glos_error_log)中。完成调试后禁用此选项。

- 如果由于死锁而失败，请始终准备好重新发出事务。死锁并不危险。再试一次。

- **保持交易小且持续时间短**，以减少它们发生冲突的可能性。

- 在进行一组相关**更改后立即提交事务**，以降低它们发生冲突的可能性。特别是，不要让交互式 [**mysql**](https://dev.mysql.com/doc/refman/5.6/en/mysql.html)会话在未提交事务的情况下长时间打开。

- 如果您使用[锁定读取](https://dev.mysql.com/doc/refman/5.6/en/glossary.html#glos_locking_read)（[`SELECT ... FOR UPDATE`](https://dev.mysql.com/doc/refman/5.6/en/select.html)或 [`SELECT ... LOCK IN SHARE MODE`](https://dev.mysql.com/doc/refman/5.6/en/select.html)），请尝试使用较低的隔离级别，例如[`READ COMMITTED`](https://dev.mysql.com/doc/refman/5.6/en/innodb-transaction-isolation-levels.html#isolevel_read-committed).

- 当修改一个事务中的多个表，或同一个表中的不同行集时，每次都**以一致的顺序执行**这些操作。然后事务形成定义明确的队列并且不会死锁。例如，将数据库操作组织成应用程序中的函数，或调用存储的例程，而不是在不同的地方编写多个相似的 `INSERT`、`UPDATE`和 `DELETE`语句序列。

- 为您的表添加精心挑选的**索引**，以便您的查询**扫描更少的索引记录并设置更少的锁**。用于 [`EXPLAIN SELECT`](https://dev.mysql.com/doc/refman/5.6/en/explain.html)确定 MySQL 服务器认为哪些索引最适合您的查询。

- **使用较少的锁定**。如果您有能力允许 a [`SELECT`](https://dev.mysql.com/doc/refman/5.6/en/select.html)从旧快照返回数据，请不要在其中添加`FOR UPDATE`or `LOCK IN SHARE MODE`子句。在这里使用[`READ COMMITTED`](https://dev.mysql.com/doc/refman/5.6/en/innodb-transaction-isolation-levels.html#isolevel_read-committed) 隔离级别很好，因为同一事务中的每个一致读取都从其自己的新快照中读取。

- 如果没有其他帮助，请使用表级锁序列化您的事务。[`LOCK TABLES`](https://dev.mysql.com/doc/refman/5.6/en/lock-tables.html)使用事务表（例如表）的正确方法是使用 (not ) 后跟`InnoDB` 开始事务，并且在显式提交事务之前不要调用 。例如，如果您需要写入 table 并从 table 读取 ，您可以这样做： `SET autocommit = 0`[`START TRANSACTION`](https://dev.mysql.com/doc/refman/5.6/en/commit.html)[`LOCK TABLES`](https://dev.mysql.com/doc/refman/5.6/en/lock-tables.html)[`UNLOCK TABLES`](https://dev.mysql.com/doc/refman/5.6/en/lock-tables.html)`t1``t2`

  ```sql
  SET autocommit=0;
  LOCK TABLES t1 WRITE, t2 READ, ...;
  ... do something with tables t1 and t2 here ...
  COMMIT;
  UNLOCK TABLES;
  ```

  表级锁可防止对表的并发更新，避免死锁，但会降低对繁忙系统的响应速度。

- 序列化事务的另一种方法是创建一个仅包含一行的辅助“信号量”表。让每个事务在访问其他表之前更新该行。这样，所有交易都以串行方式发生。请注意，`InnoDB` 即时死锁检测算法也适用于这种情况，因为序列化锁是行级锁。使用 MySQL 表级锁，必须使用 timeout 方法来解决死锁。