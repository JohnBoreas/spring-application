CentOS 仅发行版本中的内核支持 Docker。
Docker 运行在 CentOS 7 上，要求系统为64位、系统内核版本为 3.10 以上。
Docker 运行在 CentOS-6.5 或更高的版本的 CentOS 上，要求系统为64位、系统内核版本为 2.6.32-431 或者更高版本。



Linux查看系统

```shell
## 查看内核（内核版本号、硬件架构、主机名称和操作系统类型等）
uname -r
## 查看已安装的CentOS版本信息
lsb_release -a
## linux版本
cat /etc/redhat-release
```

CentOS6.8安装Docker

```shell
yum install -y epel-release
yum install -y docker-io
## 安装后的配置文件：
/etc/sysconfig/docker
#镜像地址 other_args="--registry-mirror=https://你自己的账号加速信息.mirror.aliyuncs.com"
## 启动Docker后台服务：
service docker start
## 验证
docker version
```

CentOS7以上安装Docker

```shell
## 安装gcc相关
yum -y install gcc
yum -y install gcc-c++
## 卸载旧版本
yum -y remove docker docker-common docker-selinux docker-engine
## 安装需要的软件包
yum install -y yum-utils device-mapper-persistent-data lvm2
## 设置stable镜像仓库
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
## 更新yum软件包索引
yum makecache fast
## 安装docker-ce（社区版docker）
yum -y install docker-ce
## 启动docker
systemctl start docker
## 测试
docker version
docker run hello-world:latest
## 配置镜像加速
mkdir -p /etc/docker
vim  /etc/docker/daemon.json
#网易云{"registry-mirrors": ["http://hub-mirror.c.163.com"] }
#阿里云{"registry-mirrors": ["https://｛自已的编码｝.mirror.aliyuncs.com"]}
systemctl daemon-reload
systemctl restart docker
## 卸载
systemctl stop docker 
yum -y remove docker-ce
rm -rf /var/lib/docker
```



阿里云镜像加速

```
1.链接：https://dev.aliyun.com/search.html
进入管理控制台获取链接

2.vim /etc/sysconfig/docker
将获得的自己账户下的阿里云加速地址配置进
other_args="--registry-mirror=https://你自己的账号加速信息.mirror.aliyuncs.com"

3.重新启动Docker后台服务：service docker restart
```

![image-20210218213815138](../..\java-senior-pro\resource\阿里云镜像.png)

```


```

