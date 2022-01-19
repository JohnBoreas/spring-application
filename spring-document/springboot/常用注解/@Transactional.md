@Transactional 实质是使用了 JDBC 的事务来进行事务控制的

@Transactional 基于 Spring 的动态代理的机制

```java
public interface TransactionDefinition {
    int getPropagationBehavior(); // 返回事务的传播行为
    int getIsolationLevel(); // 返回事务的隔离级别，事务管理器根据它来控制另外一个事务可以看到本事务内的哪些数据
    int getTimeout();  // 返回事务必须在多少秒内完成
    boolean isReadOnly(); // 事务是否只读，事务管理器能够根据这个返回值进行优化，确保事务是只读的
} 
```

实现原理


```sql
@Transactional 实现原理：

1) 事务开始时，通过AOP机制，生成一个代理connection对象，

   并将其放入 DataSource 实例的某个与 DataSourceTransactionManager 相关的某处容器中。

   在接下来的整个事务中，客户代码都应该使用该 connection 连接数据库，

   执行所有数据库命令。

   [不使用该 connection 连接数据库执行的数据库命令，在本事务回滚的时候得不到回滚]

  （物理连接 connection 逻辑上新建一个会话session；

   DataSource 与 TransactionManager 配置相同的数据源）


2) 事务结束时，回滚在第1步骤中得到的代理 connection 对象上执行的数据库命令，

   然后关闭该代理 connection 对象。

  （事务结束后，回滚操作不会对已执行完毕的SQL操作命令起作用）
```



**@Transactional注解**

*@Transactional属性*

| 属性                   | 类型                               | 描述                                   |
| :--------------------- | ---------------------------------- | -------------------------------------- |
| value                  | String                             | 可选的限定描述符，指定使用的事务管理器 |
| propagation            | enum: Propagation                  | 可选的事务传播行为设置                 |
| isolation              | enum: Isolation                    | 可选的事务隔离级别设置                 |
| readOnly               | boolean                            | 读写或只读事务，默认读写               |
| timeout                | int (in seconds granularity)       | 事务超时时间设置                       |
| rollbackFor            | Class对象数组，必须继承自Throwable | 导致事务回滚的异常类数组               |
| rollbackForClassName   | 类名数组，必须继承自Throwable      | 导致事务回滚的异常类名字数组           |
| noRollbackFor          | Class对象数组，必须继承自Throwable | 不会导致事务回滚的异常类数组           |
| noRollbackForClassName | 类名数组，必须继承自Throwable      | 不会导致事务回滚的异常类名字数组       |