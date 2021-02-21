​		镜像是一种轻量级、可执行的独立软件包，用来打包软件运行环境和基于运行环境开发的软件，它包含运行某个软件所需的所有内容，包括代码、运行时、库、环境变量和配置文件。



##### 特点：

```
Docker镜像都是只读的
当容器启动时，一个新的可写层被加载到镜像的顶部。
这一层通常被称作“容器层”，“容器层”之下的都叫“镜像层”。
```



##### UnionFS（联合文件系统）：

​		Union文件系统（UnionFS）是一种分层、轻量级并且高性能的文件系统，它支持对文件系统的修改作为一次提交来一层层的叠加，同时可以将不同目录挂载到同一个虚拟文件系统下(unite several directories into a single virtual filesystem)。

​		Union 文件系统是 Docker 镜像的基础。镜像可以通过分层来进行继承，基于基础镜像（没有父镜像），可以制作各种具体的应用镜像。

特性：一次同时加载多个文件系统，但从外面看起来，只能看到一个文件系统，联合加载会把各层文件系统叠加起来，这样最终的文件系统会包含所有底层的文件和目录



##### Docker镜像加载原理：

​		docker的镜像实际上由一层一层的文件系统组成，这种层级的文件系统UnionFS。

​		bootfs(boot file system)主要包含bootloader和kernel, bootloader主要是引导加载kernel, Linux刚启动时会加载bootfs文件系统，在Docker镜像的最底层是bootfs。这一层与我们典型的Linux/Unix系统是一样的，包含boot加载器和内核。当boot加载完成之后整个内核就都在内存中了，此时内存的使用权已由bootfs转交给内核，此时系统也会卸载bootfs。

​		rootfs (root file system) ，在bootfs之上。包含的就是典型 Linux 系统中的 /dev, /proc, /bin, /etc 等标准目录和文件。rootfs就是各种不同的操作系统发行版，比如Ubuntu，Centos等等。





**为什么docker这里CentOS才200M？？**

​		对于一个精简的OS，rootfs可以很小，只需要包括最基本的命令、工具和程序库就可以了，因为底层直接用Host的kernel，自己只需要提供 rootfs 就行了。由此可见对于不同的linux发行版, bootfs基本是一致的, rootfs会有差别, 因此不同的发行版可以公用bootfs。

**为什么 Docker 镜像要采用这种分层结构呢？**

​		共享资源，只需加载一份 base 镜像，就可以为所有容器服务了。而且镜像的每一层都可以被共享。



##### Docker镜像commit操作补充

```shell
docker commit提交容器副本使之成为一个新的镜像

docker commit -m=“提交的描述信息” -a=“作者” 容器ID 要创建的目标镜像名:[标签名]

## 案例
1.从Hub上下载tomcat镜像到本地并成功运行
docker run -it -p 8080:8080 tomcat	（-p 主机端口:docker容器端口；i:交互；t:终端）

2.故意删除上一步镜像生产tomcat容器的文档

3.也即当前的tomcat运行实例是一个没有文档内容的容器，以它为模板commit一个没有doc的tomcat新镜像atguigu/tomcat02

4.启动我们的新镜像并和原来的对比
启动atguigu/tomcat02，它没有docs
新启动原来的tomcat，它有docs
```

