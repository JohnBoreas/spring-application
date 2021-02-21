总体步骤:

搜索镜像

拉取镜像

查看镜像

启动镜像

停止容器

移除容器



安装tomcat

```shell
1.docker hub上面查找tomcat镜像
docker search tomcat
2.从docker hub上拉取tomcat镜像到本地
docker pull tomcat
3.docker images查看是否有拉取到的tomcat
4.使用tomcat镜像创建容器(也叫运行镜像)
docker run -it -p 8080:8080 tomcat
```

安装mysql

```
1.docker hub上面查找mysql镜像
2.从docker hub上(阿里云加速器)拉取mysql镜像到本地标签为5.6
3.使用mysql5.6镜像创建容器(也叫运行镜像)
docker run -p 12345:3306 --name mysql -v /zzyyuse/mysql/conf:/etc/mysql/conf.d -v /zzyyuse/mysql/logs:/logs -v /zzyyuse/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.6
 
命令说明：
-p 12345:3306：将主机的12345端口映射到docker容器的3306端口。
--name mysql：运行服务名字
-v /zzyyuse/mysql/conf:/etc/mysql/conf.d ：将主机/zzyyuse/mysql录下的conf/my.cnf 挂载到容器的 /etc/mysql/conf.d
-v /zzyyuse/mysql/logs:/logs：将主机/zzyyuse/mysql目录下的 logs 目录挂载到容器的 /logs。
-v /zzyyuse/mysql/data:/var/lib/mysql ：将主机/zzyyuse/mysql目录下的data目录挂载到容器的 /var/lib/mysql 
-e MYSQL_ROOT_PASSWORD=123456：初始化 root 用户的密码。
-d mysql:5.6 : 后台程序运行mysql5.6
```

安装redis

```
1.docker run -p 6379:6379 -v /zzyyuse/myredis/data:/data -v /zzyyuse/myredis/conf/redis.conf:/usr/local/etc/redis/redis.conf  -d redis:3.2 redis-server /usr/local/etc/redis/redis.conf --appendonly yes

2.在主机/zzyyuse/myredis/conf/redis.conf目录下新建redis.conf文件
vim /zzyyuse/myredis/conf/redis.conf/redis.conf

3.测试redis-cli连接上来
 docker exec -it 运行着Rediis服务的容器ID redis-cli
4.测试持久化文件生成

```

