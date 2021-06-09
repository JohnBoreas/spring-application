#### 一、Solr集合和核心

```shell
## 创建一个核心或集合
bin/solr create -c hollow -p 6983 
-c <name> 要创建的核心或集合的名称（必需）。
-d <confdir> 配置目录。默认为_default
-n <configName> 配置名称。与内核或集合的默认名称相同
-p <port> 发送 create 命令的本地 Solr 实例的端口, 一台机器有多个solr实例时用
-s <shards> 或者 -shards ：分割集合的分片数量，默认值为1

## 删除核心或集合
bin/solr delete -c hollow -p 6983 
-deleteConfig 是否也应该从 ZooKeeper 中删除配置目录。默认是true
-p <port> 要向其发送 delete 命令的本地 Solr 实例的端口
```

#### 二、启动和停止Solr

```shell
## 启动
bin/solr start -m 4g -s ${dir} -p ${port} -a "-Xdebug -Xrunjdwp:transport=dt_socket, server=y,suspend=n,address=1044"
-a "<string>" 使用额外的 JVM 参数（例如以 -X 开头的参数）启动 Solr
-d <dir> 定义一个服务器目录，默认为server（如，$SOLR_HOME/server）
-f 在前台启动 Solr；在使用 -e 选项运行示例时，不能使用此选项
-h <hostname> 用定义的主机名启动 Solr
-m <memory> 以定义的值启动 Solr ：JVM 的 min（-Xms）和 max（-Xmx）堆大小
-p <port> 在定义的端口上启动 Solr
-s <dir> 设置 solr.solr.home 系统属性；Solr 将在这个目录下创建核心目录。默认值是server/solr。
		 如果设置，则除非 ZooKeeper 中存在 solr.xml，否则指定的目录应包含 solr.xml 文件
-q 要更安静。这将 log4j 的日志记录级别从INFO更改为WARN
-v 比较详细。这将 log4j 的日志记录级别从INFO更改为DEBUG

## 停止 Solr
bin/solr stop -p 8983
-p <port> 停止在给定端口上运行 Solr
-all 停止所有正在运行的具有有效 PID 的 Solr 实例
-k <key> 停止键用于防止无意中停止 Solr；默认是 “solrrocks”
```

#### 三、Solr系统信息

```shell
## 版本信息
bin/solr version
## 状态
bin/solr status
## 健康检查
bin/solr healthcheck -c gettingstarted -z localhost:9865
-c <集合>（要求）运行健康检查的集合的名称
-z <zkhost> ZooKeeper连接字符串，默认为localhost:9983
```

