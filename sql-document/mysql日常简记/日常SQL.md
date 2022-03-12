1、IP转换

select inet_aton('192.168.0.1');

select inet_ntoa(3232235521);

2、隔离级别

select @@tx_isolation;

3、获取死锁日志

show engine innodb status;

```javascript
## 数据库版本
select version()
## 数据库引擎
show variables like '%engine%';
## 事务隔离级别
select @@global.tx_isolation, @@session.tx_isolation, @@tx_isolation;
## 查看gap锁开启状态
show variables like 'innodb_locks_unsafe_for_binlog';
## 查看自增锁模式
show variables like 'innodb_autoinc_lock_mode';
```

