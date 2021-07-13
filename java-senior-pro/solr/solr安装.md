##### solr提供2种模式的安装

1、基于自带jetty启动

2、配置tomcat启动solr



##### 一、Tomcat

1、引入lib包与war包

```
1、将solr解压包./server/solr-webapp/webapp/下的内容拷贝到刚才tomcat下新建的文件夹solr中
2、将./server/lib/ext/下的jar包拷贝到/tomcat/webapps/solr/WEB-INF/lib/下
3、将./server/lib/下metrics开头的jar包也拷贝到刚才的lib目录下
4、将./dist/下 solr-dataimporthandler-*.jar拷贝到刚才的lib目录下
```

2、配置web.xml

Tomcat/webapps/solr/WEB-INF下的web.xml

```xml
<env-entry>
       <env-entry-name>solr/home</env-entry-name>
       <env-entry-value>{solrhome dir}</env-entry-value>
       <env-entry-type>java.lang.String</env-entry-type>
</env-entry>
```



##### 二、自带Jetty启动

1、tar zxvf solr-8.8.2解压文件

2、新建run.sh，并赋予文件执行权限

chmod -R 777 run.sh

```shell script
echo "pwd is "`pwd`
JETTY_PORT=8983
JETTY_STOP_PORT=6988
JETTY_STOP_KEY=123456
JETTY_LOG_PROP_PATH=./etc/logging.properties
SOLR_HOME=./server/solr
SOLR_DATA_DIR=./server/solr/data
# -Dsolr.data.dir=${SOLR_DATA_DIR} \

if [ "$1" == "start" ]; then
    echo "start runing"
    ./bin/solr start -m 4g   \
         -s ${SOLR_HOME}     \  // 指定solr的code目录
         -p ${JETTY_PORT}
    echo $?
elif [ "$1" == "stop" ]; then
    echo "start stoping"
    ./bin/solr stop -p ${JETTY_PORT}

    echo $?
else
    echo "sorry, stop or start, say it."
fi
echo $?
```



##### 三、检查状态

3、常用命令

```shell script
## 查看solr状态
bin/solr status
```



##### 四、solr配置

4、创建code

```shell
在./server/solr目录下新建code文件夹，然后复制conf 后，再创建（将example下的code中的conf目录复制过来）

## admin控制台创建
打开solr控制台 http://localhost:8080/solr/index.html#/ 
选择Core Admin，注意name和instanceDir要跟刚刚创建的文件夹名称一样。

## 命令行创建soooo code
bin/solr create -c soooo
```

5、配置schema

Schema API其实就是用post请求向solr服务器发送携带json参数的请求

<http://192.168.0.175:8983/solr/hollow/schema>

```
配置managed-schema（5.5之前为schema.xml）（conf下）
```

6、分词器（中文分词器）

（1）下载好的分词器jar包，放入./server/solr-webapp/webapp/WEB-INF/lib目录中

（2）修改./solr/mycore/conf目录中打开managed-schema文件

```xml

<!-- ik分词器 -->
<fieldType name="text_ik" class="solr.TextField">
    <analyzer type="index">
        <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory" useSmart="false" conf="ik.conf"/>
        <filter class="solr.LowerCaseFilterFactory"/>
    </analyzer>
    <analyzer type="query">
        <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory" useSmart="true" conf="ik.conf"/>
        <filter class="solr.LowerCaseFilterFactory"/>
    </analyzer>
</fieldType>
```

7、连接mysql（看需求）

（1）core目录下，创建lib文件夹，并引入mysql依赖jar包

​         将solr-dataimporthandler-*.jar（2个）拷贝至lib下（包在solr安装目录的dist下）

（2）从solr的example/example-DIH/solr/db/conf 复制db-data-config.xml，到core/conf目录下

（3）配置db-data-config.xml 设置mysql驱动，url路径，用户名，密码。entity的name根据需求命名，query是查询到solr中sql语句
```xml
<dataConfig>
    <dataSource type="JdbcDataSource" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://192.168.0.1:3306/mysql" user="root" password="root"/>
    <document>
        <entity name="item_7" query="SELECT * FROM item_7 ">
            <!-- column是数据库字段，name是solr定义的域 -->
            <field column="title" name="title"/>
        </entity>
    </document>
</dataConfig>
```
（4）修改schema.xml，设置DataImportHandler。必须要配置在/select 前面

```xml
<requestHandler name="/dataimport" class="solr.DataImportHandler">
     <lst name="defaults">
        <str name="config">db-data-config.xml</str>
     </lst>
</requestHandler>
```

