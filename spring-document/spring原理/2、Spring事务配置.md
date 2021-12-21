Spring配置声明式事务：

\* 配置DataSource
\* 配置事务管理器
\* 事务的传播特性
\* 那些类那些方法使用事务

Spring配置文件中关于事务配置总是由三个组成部分，分别是DataSource、TransactionManager和代理机制这三部分，无论哪种配置方式，一般变化的只是代理机制这部分。





Spring事务的配置又有几种不同的方式：

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