#### Mycat：数据库中间件

原理：

最重要的是“`拦截`”，拦截了用户发送过来的SQL语句，对SQL语句做特定分析，然后将此SQL发往后端的真实数据库，并将返回的结果做适当的处理，最终再返回给用户。





mycat与sharding-jdbc的区别：

sharding-jdbc作为一个组件集成在应用内，而mycat则作为一个独立的应用需要单独部署。

从架构上看sharding-jdbc更符合分布式架构的设计，直连数据库，没有中间应用，理论性能是最高的。

从耦合性来看，sharding-jdbc需要集成在应用内，集成到代码里，使得开发成本相对较高；而mycat大致上是无损开发，不需要怎么修改代码，交给mycat去托管，分库分表等代码无需太多关注。



#### Sharding-jdbc

原理：SQL解析 => 查询优化 => SQL路由 => SQL改写 => SQL执行 => 结果归并 ，最终返回执行结果。



分库分表，主要是配置application.properties

可以数据库分片，数据库分表，主从等



遇到的问题：

（1） Cannot get uniformed table structure for `tb_item`. The different meta data of actual tables are as follows:

断点查看，

提示找不到表的元数据，找不到表结构，看看是不是哪个表没创建

【sharding会进行数据库字段校验的，检查各个分表的数据类型是否一致】



（2）ShardingException: Cannot find data source in sharding rule, invalid actual data node is

配置了主库就要配置从库，否则报错

```properties
#主从库逻辑数据源定义ds_0----》sharding-jdbc1-master，sharding-jdbc1-slave1, sharding-jdbc1-slave2
sharding.jdbc.config.sharding.master-slave-rules.ds_0.master-data-source-name=sharding-jdbc1-master
sharding.jdbc.config.sharding.master-slave-rules.ds_0.slave-data-source-names=sharding-jdbc1-slave1, sharding-jdbc1-slave2
```



（3）java.lang.String.mod() is applicable for argument types: (java.lang.Integer) values: [2]

分库策略字段需要注意字段类型

是字符串还是数值

