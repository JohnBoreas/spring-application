事务隔离级别

DEFAULT ：使用数据库设置的隔离级别 ( 默认 ) ，由 DBA 默认的设置来决定隔离级别 .

READ_UNCOMMITTED ：会出现脏读、不可重复读、幻读 ( 隔离级别最低，并发性能高 )

READ_COMMITTED ：会出现不可重复读、幻读问题（锁定正在读取的行）

REPEATABLE_READ ：会出幻读（锁定所读取的所有行）

SERIALIZABLE ：保证所有的情况不会发生（锁表）



事务的7种传播级别：

1. TransactionDefinition.PROPAGATION_REQUIRED：
   如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值。

2. TransactionDefinition.PROPAGATION_REQUIRES_NEW：
   创建一个新的事务，如果当前存在事务，则把当前事务挂起。

3. TransactionDefinition.PROPAGATION_SUPPORTS：
   如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。

4. TransactionDefinition.PROPAGATION_NOT_SUPPORTED：
   以非事务方式运行，如果当前存在事务，则把当前事务挂起。

5. TransactionDefinition.PROPAGATION_NEVER：
   以非事务方式运行，如果当前存在事务，则抛出异常。

6. TransactionDefinition.PROPAGATION_MANDATORY：
   如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。

7. TransactionDefinition.PROPAGATION_NESTED：
   如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；
   如果当前没有事务，则该取值等价于TransactionDefinition.PROPAGATION_REQUIRED。