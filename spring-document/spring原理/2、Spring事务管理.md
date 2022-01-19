**事务管理方式**

spring支持编程式事务管理和声明式事务管理两种方式。



**编程式事务**

使用TransactionTemplate或者直接使用底层的PlatformTransactionManager。

需要在代码中显式调用beginTransaction()、commit()、rollback()等

对于编程式事务管理，spring推荐使用TransactionTemplate。



**Spring配置声明式事务**：

\* 配置DataSource
\* 配置事务管理器
\* 事务的传播特性
\* 那些类那些方法使用事务

Spring配置文件中关于事务配置总是由三个组成部分，分别是DataSource、TransactionManager和代理机制这三部分，无论哪种配置方式，一般变化的只是代理机制这部分。



**自动提交(AutoCommit)与连接关闭时的是否自动提交**

*自动提交*

默认情况下，数据库处于自动提交模式。每一条语句处于一个单独的事务中，在这条语句执行完毕时，如果执行成功则隐式的提交事务，如果执行失败则隐式的回滚事务

*连接关闭时的是否自动提交*

C3P0默认的策略是回滚任何未提交的事务。autoCommitOnClose=false



##### 事务超时

事务不能运行太长的时间。长事务会占用数据库资源。自动回滚，而不是一直等待其结束。



##### 回滚规则

默认情况下，事务只有遇到运行期异常时才会回滚



**spring事务特性**

spring所有的事务管理策略类都继承自org.springframework.transaction.PlatformTransactionManager接口



**Spring事务的配置方式：**

第一种方式：每个Bean都有一个代理

 第二种方式：所有Bean共享一个代理基类

第三种方式：使用拦截器

第四种方式：使用tx标签配置的拦截器

第五种方式：全注解





1、spring事务控制放在service层，在service方法中一个方法调用service中的另一个方法，默认开启几个事务？

spring的事务传播方式默认是PROPAGATION_REQUIRED，判断当前是否已开启一个新事务，有则加入当前事务，否则新开一个事务（如果没有就开启一个新事务），所以答案是开启了一个事务。



2、spring 什么情况下进行事务回滚？

Spring、EJB的声明式事务默认情况下都是在抛出unchecked exception后才会触发事务的回滚

unchecked异常,即运行时异常runntimeException 回滚事务;

checked异常,即Exception可try{}捕获的不会回滚.当然也可配置spring参数让其回滚.





#### 多线程事务管理
1、描述

​	因为线程不属于spring托管，故线程不能够默认使用spring的事务,也不能获取spring注入的bean

​	在被spring声明式事务管理的方法内开启多线程，多线程内的方法不被事务控制

2、解决

​	如果方法中调用多线程

​			方法主题的事务不会传递到线程中

​			线程中可以单独调用Service接口，接口的实现方法使用@Transactional，保证线程内部的事务

​	多线程实现的方法

​			使用异步注解@Async的方法上再加上注解@Transactional，保证新线程调用的方法是有事务管理的

3、原理

​	Spring中事务信息存储在ThreadLocal变量中，变量是某个线程上进行的事务所特有的(这些变量对于其他线程中发生的事务来讲是不可见的，无关的)

​	单线程的情况下，一个事务会在层级式调用的Spring组件之间传播

​	在@Transactional注解的服务方法会产生一个新的线程的情况下，事务是不会从调用者线程传播到新建线程的

