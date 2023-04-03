INSERT导致的死锁：

假设一个`InnoDB`表 `t1`有如下结构：

```sql
CREATE TABLE t1 (i INT, PRIMARY KEY (i)) ENGINE = InnoDB;
```

现在假设三个会话依次执行以下操作：

第 1 节：

```sql
START TRANSACTION;
INSERT INTO t1 VALUES(1);
```

第 2 节：

```sql
START TRANSACTION;
INSERT INTO t1 VALUES(1);
```

第 3 节：

```sql
START TRANSACTION;
INSERT INTO t1 VALUES(1);
```

第 1 节：

```sql
ROLLBACK;
```

会话 1 的第一个操作获取该行的独占锁。会话 2 和会话 3 的操作都导致重复键错误，并且它们都请求该行的共享锁。当会话 1 回滚时，它会释放对该行的独占锁，并授予会话 2 和 3 的排队共享锁请求。此时，会话 2 和会话 3 死锁：由于对方持有共享锁，因此无法获取该行的排他锁。



如果表中已经包含键值为 1 的行，并且三个会话依次执行以下操作，则会出现类似情况：

第 1 节：

```sql
START TRANSACTION;
DELETE FROM t1 WHERE i = 1;
```

第 2 节：

```sql
START TRANSACTION;
INSERT INTO t1 VALUES(1);
```

第 3 节：

```sql
START TRANSACTION;
INSERT INTO t1 VALUES(1);
```

第 1 节：

```sql
COMMIT;
```

会话 1 的第一个操作获取该行的独占锁。会话 2 和会话 3 的操作都导致重复键错误，并且它们都请求该行的共享锁。当会话 1 提交时，它会释放对该行的独占锁，并授予会话 2 和 3 的排队共享锁请求。此时，会话 2 和会话 3 死锁：由于对方持有共享锁，因此无法获取该行的排他锁。

