1、目录结构

#### nexus-3.5.2-01 目录

```shell
bin 	包含nexus的启动脚本和相关配置
etc 	jetty、karaf等配置文件
jre 	jre环境
lib 	java架包库
public 	关于nexus应用在本地跑起来所需要的资源
system 应用所有的插件和组件
LICENSE.txt 和 NOTICE.txt 版权声明和法律细则
```

#### sonatype-work\nexus3 目录

```shell
blobs/ 	创建blob的默认路径，当然也可以重新指定
cache/ 	当前缓存的karaf包的信息
db/ 	OrientDB数据库的数据，用于存储nexus的元数据的数据库
elasticsearch/ 	当前配置的Elasticsearch状态
etc/ 	大概是运行时配置状态和关于资源库的自定义的相关的东西
health-check/ 	看目录，健康检查的相关报告的存储目录吧
keystores/ 	自动生成的关于资源库的ID主键
log/ 	运行实例生成的日志文件，也有日志文件的压缩包，貌似是每天都会生成日志文件，你可以定期删除老的日志文件
tmp/ 	用于存储临时文件的目录
```



2、配置Nexus

![img](..\resource\nexus.png)



Blob Strores----用于设置文件存储目录



Repository----创建仓库

Proxy仓库

![img](..\resource\nexus-proxy.png)

![img](..\resource\nexus-proxy-1.png)

hosted仓库

![img](..\resource\nexus-hosted.png)

group仓库组（注意：group资源库的**顺序**应是：`hosted仓库` > `国内proxy仓库` > `国外proxy仓库`）

![img](..\resource\nexus-group.png)





下载Jar：设置 Nexus 为镜像地址

Maven->setting.xml

```xml
<!--自定义maven本地仓库地址-->
  <localRepository>D:\apps\repository</localRepository>
  <!--nexus服务器-->
  <servers>  
    <server>  
        <id>nexus</id>  
        <username>admin</username>  
        <password>admin123</password>  
    </server>   
     <server>  
        <id>nexus-releases</id>  
        <username>admin</username>  
        <password>admin123</password>  
  	</server>  
  	<server>  
        <id>nexus-snapshots</id>  
        <username>admin</username>  
        <password>admin123</password>  
  	</server>     
  </servers>  
  <!--仓库组的url地址  id和name自定义，mirrorOf的值设置为central，写死的-->  
  <mirrors>     
    <mirror>  
        <id>nexus</id>  
        <name>nexus repository</name>  
        <url>http://localhost:8081/repository/myself_group/</url>  
        <mirrorOf>central</mirrorOf>  
    </mirror>     
  </mirrors>  
```

部署上传Jar：配置distributionManagement

pom.xml

```xml
<project>
...
<distributionManagement>
  <repository>
    <id>nexus-releases</id>
      <name>Nexus Release Repository</name>
      <url>http://localhost:8081/nexus/content/repositories/releases/</url>
  </repository>
  <snapshotRepository>
    <id>nexus-snapshots</id>
    <name>Nexus Snapshot Repository</name>
    <url>http://localhost:8081/nexus/content/repositories/snapshots/</url>
  </snapshotRepository>
</distributionManagement>
...
</project>
```





#### 管理平台上传三方jar包

![在这里插入图片描述](..\resource\nexus-uploadpng)

