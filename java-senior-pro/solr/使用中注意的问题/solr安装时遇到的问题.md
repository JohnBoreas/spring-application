#### 1、安装提示SOLR_ULIMIT_CHECKS to false

```shell script
start runing
*** [WARN] *** Your open file limit is currently 1024.
 It should be set to 65000 to avoid operational disruption.
 If you no longer wish to see this warning, set SOLR_ULIMIT_CHECKS to false in your profile or solr.in.sh
*** [WARN] ***  Your Max Processes Limit is currently 30484.
 It should be set to 65000 to avoid operational disruption.
 If you no longer wish to see this warning, set SOLR_ULIMIT_CHECKS to false in your profile or solr.in.sh
WARNING: Starting Solr as the root user is a security risk and not considered best practice. Exiting.
         Please consult the Reference Guide. To override this check, start with argument '-force'
```
需要设置SOLR_ULIMIT_CHECKS=false（solr.in.sh）或则将ulimit设置为65000

Solr可以使用许多进程和许多文件句柄。设定值小，导致Solr不稳定。SOLR_ULIMIT_CHECKS=false关闭这些检查

#### 2、solr启动不建议使用root账号，存在安全性问题

```shell script
必须使用root账号启动则：
./solr start -force
```

#### 3、新建code

必须要在./server/solr目录下新建code文件夹，然后复制conf 后，再创建

（可以选择solr-8.8.2\server\solr\configsets\_default\conf目录下的所有文件）

```shell
## admin控制台创建
1、在./server/solr目录下新建code文件夹，然后复制conf进入该目录
2、打开solr控制台 http://localhost:8080/solr/index.html#/ 选择Core Admin，注意name和instanceDir要跟刚刚创建的文件夹名称一样。
## 命令行创建
bin/solr create -c soooo
```

