软件安装需要开启防火墙的详见mysql安装部分



**java安装**

```
（1）下载安装包，使用scp工具上传文件到服务器

（2）解压文件：
 $ tar zxvf jdk-8u191-linux-x64.tar.gz

（3）使用Vi编辑器，设置环境变量
​ $ sudo vi /etc/profile
​ 在文件最后，添加如下内容：
\#Java Env export JAVA_HOME=/usr/jdk1.8.0_121 export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar export PATH=$PATH:$JAVA_HOME/bin

（4）退出vi编辑器，并保存，输入命令使环境变量设置立即生效
​ $ source /etc/profile

（5）查看JDK版本
$ java -version
java version "1.8.0_121" Java(TM) SE Runtime Environment (build 1.8.0_121-b13) Java HotSpot(TM) 64-Bit Server VM (build 25.121-b13, mixed mode)
```

**Tomcat安装**

```
（1）解压
tar -zxvf apache-tomcat-8.0.50.tar.gz

（2）进入tomcat安装bin目录并启动
./startup.sh

（3）关闭服务器
./shutdown.sh
【存在问题：jdk1.8与tomcat8.5，tomcat启动延迟，非内存原因】
http://47.99.74.103:8080/

（4）jdk路径配置
在bin下的catalina.bat文件
修改tomcat(在bin目录下)catalina.sh文件和setclasspath.sh文件开头的空白处加上如下两句(指定JDK)：
 export JAVA_HOME=/usr/local/jdk1.8.0_151
 export JRE_HOME=/usr/local/jdk1.8.0_151/jre
```

**Redis安装**

```
（1）下载
wget http://download.redis.io/releases/redis-5.0.0.tar.gz

（2）解压
tar xzf redis-5.0.0.tar.gz

（3）进入redis目录下执行make编译
$ cd redis-5.0.0
$ make
【#直接make 编译 make
​  \#可使用root用户执行`make install`，将可执行文件拷贝到/usr/local/bin目录下。这样就可以直接敲名字运行程序了。
make install】

（4）启动
使用默认配置启动：$ src/redis-server
使用指定配置启动：$ ./src/redis-server redis.conf

（5）退出
解决方法１：　直接按CTRL＋C
解决方法２：　把配置文件改成后台启动　vim /etc/redis.conf
		把 daemonize no 改成 daemonize yes

（6）连接redis
$ ./redis-cli -h 【ip地址】 -p 【端口】

（7）关闭服务器
\#使用客户端
redis-cli shutdown
\#因为Redis可以妥善处理SIGTERM信号，所以直接kill -9也是可以的
kill -9 PID

（8）外部远程连接（非本地）
注释掉：bin 127.0.0.1
找到这一项 protected-mode yes  把这一项的yes 改成 no
```

**MySQL安装**

```shell
（1）安装前准备
检查是否安装过mysql
$ rpm -qa | grep mysql
删除原来的数据库
$ rpm -e --nodeps mysql-libs-5.1.73-5.el6_6.x86_64 // 后面是安装的数据库版本
下载安装包
$ wget https://dev.mysql.com/get/Downloads/MySQL-5.7/mysql-5.7.24-linux-glibc2.12-x86_64.tar.gz
（2）解压
$ tar xzvf mysql-5.7.24-linux-glibc2.12-x86_64.tar.gz

（3）在mysql目录下创建data目录
$ mkdir /usr/local/mysql/data

（4）更改mysql目录下所有的目录及文件夹所属的用户组和用户，以及权限（看情况是否需要）
$ useradd -d /opt/mysql -m mysql
$ chown -R mysql:mysql /usr/local/mysql
$ chmod -R 755 /usr/local/mysql

（5）编译安装并初始化mysql
【务必记住初始化输出日志末尾的密码】日志最末尾位置root@localhost:OxrYow)xI9iZ（数据库管理员临时密码）
$ cd /usr/local/mysql/bin
$ ./mysqld --initialize --user=mysql --datadir=/usr/local/mysql/data --basedir=/usr/local/mysql
$ bin/mysql_ssl_rsa_setup  --datadir=/usr/local/mysql

注意：确认以下两个是否安装
$  yum install  libaio-devel.x86_64
$  yum -y install numactl

（6）编辑配置文件my.cnf，添加配置如下
$  vi /etc/my.cnf
    [mysqld]
    datadir=/usr/local/mysql/data
    port = 3306
    sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
    symbolic-links=0
    max_connections=400
    innodb_file_per_table=1
    #表名大小写不明感，敏感为
    lower_case_table_names=1

（7）启动mysql服务器
$ /usr/local/mysql/support-files/mysql.server start

注意：无/var/log/mariadb/mariadb.log文件需要创建
[root@vultr support-files]# mkdir /var/log/mariadb
[root@vultr support-files]# touch /var/log/mariadb/mariadb.log
[root@vultr support-files]# chown -R mysql:mysql /var/log/mariadb/

（8）添加软连接，并重启mysql服务
$  ln -s /usr/local/mysql/support-files/mysql.server /etc/init.d/mysql 
$  ln -s /usr/local/mysql/bin/mysql /usr/bin/mysql
$  service mysql restart

（9）登录mysql，修改密码(密码为步骤5生成的临时密码)
$  mysql -u root -p
Enter password:
mysql>set password for root@localhost = password('123456');

注意：如果无法登录可能需要配置mysql.sock
查看/etc/my.cnf 文件的 socket=/usr/local/mysql/mysql.sock
$ ln -s /usr/local/mysql/mysql.sock /tmp/mysql.sock

（10）开放远程连接
mysql>use mysql;
msyql>update user set user.Host='%' where user.User='root';
mysql>flush privileges;

（11）设置开机自动启动
1、将服务文件拷贝到init.d下，并重命名为mysql
$ cp /usr/local/mysql/support-files/mysql.server /etc/init.d/mysqld
2、赋予可执行权限
$ chmod +x /etc/init.d/mysqld
3、添加服务
$ chkconfig --add mysqld
4、显示服务列表
$ chkconfig --list

（11）linux开启防火墙，开放端口
云服务器进行配置firwall
$ iptables -nL --line-number
$ iptables -I IN_public_allow -p tcp --dport 3306 -j ACCEPT
```

**RabbitMQ**

```
安装Erlang
3.3.2.添加yum支持
cd /usr/local/src/
mkdir rabbitmq
cd rabbitmq

wget http://packages.erlang-solutions.com/erlang-solutions-1.0-1.noarch.rpm
rpm -Uvh erlang-solutions-1.0-1.noarch.rpm

rpm --import http://packages.erlang-solutions.com/rpm/erlang_solutions.asc

使用yum安装：
sudo yum install erlang

上传rabbitmq-server-3.4.1-1.noarch.rpm文件到/usr/local/src/rabbitmq/
安装：
rpm -ivh rabbitmq-server-3.4.1-1.noarch.rpm

3.3.4.启动、停止
service rabbitmq-server start
service rabbitmq-server stop
service rabbitmq-server restart
3.3.5.设置开机启动
chkconfig rabbitmq-server on
3.3.6.设置配置文件
cd /etc/rabbitmq
cp /usr/share/doc/rabbitmq-server-3.4.1/rabbitmq.config.example /etc/rabbitmq/

mv rabbitmq.config.example rabbitmq.config
3.3.7.开启用户远程访问
vi /etc/rabbitmq/rabbitmq.config

注意要去掉后面的逗号。
3.3.8.开启web界面管理工具
rabbitmq-plugins enable rabbitmq_management
service rabbitmq-server restart
3.3.9.防火墙开放15672端口
/sbin/iptables -I INPUT -p tcp --dport 15672 -j ACCEPT
/etc/rc.d/init.d/iptables save
```


