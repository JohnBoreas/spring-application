1、solr文件夹下内容

（1）bin

```
solr 和 solr.cmd：solr的控制脚本，启动和停止 Solr 的首选工具
```

（2）contrib

```
Solr 的contrib目录包含 Solr 专用功能的附加插件。 
```

（3）dist

```
该dist目录包含主要的 Solr .jar 文件。
```

（4）docs

```
该docs目录包括一个链接到在线 Javadocs 的 Solr。
```

（5）example

```
该example目录包括演示各种 Solr 功能的几种类型的示例。有关此目录中的内容的详细信息，请参阅下面的 Solr 示例。
```

（6）licenses

```
该licenses目录包括 Solr 使用的第三方库的所有许可证。
```

（7）server

```
此目录是 Solr 应用程序的核心所在。此目录中的 README 提供了详细的概述，但以下是一些特点：

Solr 的 Admin UI（server/solr-webapp）

Jetty 库（server/lib）

日志文件（server/logs）和日志配置（server/resources）。有关如何自定义 Solr 的默认日志记录的详细信息，请参阅配置日志记录一节。

示例配置（server/solr/configsets）
```



2、server/solr/conf文件夹

（1）managed-schema（5.5之前为schema.xml）

用于集合/内核中字段的定义，主要是让Solr知道集合/内核包含哪些字段、字段的数据类型以及该字段是否存储索引。

（2）solrconfig.xml

设置code的listener，dispatcher，requestHandler等配置



3、server/solr/lib文件夹

存放code需要引入的包，如：mysql-connector-java-5.0.5-bin.jar