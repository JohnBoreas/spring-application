产生death lock原理

insert ... on duplicate key 在执行时，innodb引擎会先判断插入的行是否产生重复key错误，如果存在，在对该现有的行加上S（共享锁）锁，如果返回该行数据给mysql,然后mysql执行完duplicate后的update操作，然后对该记录加上X（排他锁），最后进行update写入。



![img](..\resource\onduplicate死锁.png)





解决：

1、5.6不存在，5.7才有

2、使用RC级别，RC隔离级别下不会有gap锁

3、 不要使用 insert on duplicate key update，使用普通的insert

4、在数据库表中只建立主键，不建立其他唯一索引。

5、先insert 再捕获异常，然后进行更新

6、使用insert ignore，然后判断update rows 是否是1，然后再决定是否更新

 