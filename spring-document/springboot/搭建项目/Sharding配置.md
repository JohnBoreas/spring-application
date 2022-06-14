分库分表

主要是配置application.properties



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

