#### 一、数据库是否支持emoji表情，如果不支持，如何操作？

执行语句前加

```sql
SET NAMES 'utf8mb4'
```



#### 二、查询问题

一个6亿的表a，一个3亿的表b，通过外间tid关联，你如何最快的查询出满足条件的第50000到第50200中的这200条数据记录。

**1、如果A表TID是自增长,并且是连续的,B表的ID为索引**

```
select * from a,b where a.tid = b.id and a.tid>500000 limit 200;
```

**2、如果A表的TID不是连续的,那么就需要使用覆盖索引.TID要么是主键,要么是辅助索引,B表ID也需要有索引。**

```
select * from b , (select tid from a limit 50000,200) a where b.id = a .tid;
```



#### 三、drop、delete与truncate的区别

1、delete和truncate只删除表的数据不删除表的结构

2、速度,一般来说: drop> truncate >delete

3、delete语句是dml,这个操作会放到rollback segement中,事务提交之后才生效;

4、如果有相应的trigger,执行的时候将被触发. truncate,drop是ddl, 操作立即生效,原数据不放到rollback segment中,不能回滚. 操作不触发trigger.



#### 四、MySQL中myisam与innodb的区别

**(1)、问5点不同；**

> 1>.InnoDB支持事物，而MyISAM不支持事物
> 2>.InnoDB支持行级锁，而MyISAM支持表级锁
> 3>.InnoDB支持MVCC, 而MyISAM不支持
> 4>.InnoDB支持外键，而MyISAM不支持
> 5>.InnoDB不支持全文索引，而MyISAM支持。

**(2)、innodb引擎的4大特性**

> 插入缓冲（insert buffer),二次写(double write),自适应哈希索引(ahi),预读(read ahead)

**(3)、2者select count(\*)哪个更快，为什么**

> myisam更快，因为myisam内部维护了一个计数器，可以直接调取。