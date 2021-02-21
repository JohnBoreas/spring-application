Docker是基于Go语言实现的云开源项目。
Docker的主要目标是“Build，Ship and Run Any App,Anywhere”---一次封装，到处运行



**Docker解决问题**：

解决了运行环境和配置问题软件容器，方便做持续集成并有助于整体发布的容器虚拟化技术。

更快的升级，交付，部署，扩容，合理的利用计算机资源



**如何工作**

Docker是一个Client-Server结构的系统，Docker守护进程运行在主机上， 然后通过Socket连接从客户端访问，守护进程从客户端接受命令并管理运行在主机上的容器。 

容器，是一个运行时环境，就是我们前面说到的集装箱。



**虚拟机的缺点**：

1、资源占用多

2、冗余步骤多

3、启动慢



**为什么docker比虚拟机快**

（1）docker有着比虚拟机更少的抽象层。直接使用实际物理机硬件资源，不需要Hypervisor实现硬件资源虚拟化

（2）docker利用的是宿主机的内核,而不需要Guest OS。不需要和虚拟机一样重新加载一个操作系统内核。

![虚拟机与docker架构对比图.png](../..\java-senior-pro\resource\虚拟机与docker架构对比图.png)

两者对比

![docker对比虚拟机.png](../..\java-senior-pro\resource\docker对比虚拟机.png)



**Docker基本组成**

镜像（image）：一个只读的模板。镜像可以用来创建 Docker 容器，一个镜像可以创建很多容器。

容器（container）：容器是用镜像创建的运行实例。

仓库（repository）：集中存放镜像文件的场所。



**启动Docker后台容器**

![启动Docker后台容器.png](../..\java-senior-pro\resource\启动Docker后台容器.png)