##### Sola特点：

通过zookeeper进行集群管理
支持丰富的数据源，可以直接使用mysql作为数据源
和hadoop生态结合较好，可以索引文件直接落入hdfs可以实现无限扩容
自带查询功能强大，新手都能很容易上手
支持cvs，xml，就送、

##### Elasticsearch特点：

效率快，自带一些图表分析的功能
restful的查询方式
可以通过Logstash来支持mysql数据源



##### Solr和Elasticsearch主要区别：

Solr原生支持mysql数据源，ES需要Logstash来扩展支持（虽然都是一家的产品）
Elasticsearch在数据量较多的情况下速度比Solr快
数据源Solr支持的更好，ES只支持json数据源
集群上SolrCloud比较ES简单
分词上都支持中文和拼音分词
Solr webUI更方便观察服务运行情况和调试索引