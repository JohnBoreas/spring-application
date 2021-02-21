一句话：有点类似我们Redis里面的rdb和aof文件

#### 数据卷

##### 是什么？

Docker的理念：
*  将运用与运行的环境打包形成容器运行 ，运行可以伴随着容器，但是我们对数据的要求希望是持久化的
*  容器之间希望有可能共享数据



Docker容器产生的数据，如果不通过docker commit生成新的镜像，使得数据做为镜像的一部分保存下来，
那么当容器删除后，数据自然也就没有了。

为了能保存数据在docker中我们使用卷。



##### 能干什么？（容器的持久化、容器间继承+共享数据）

卷就是目录或文件，存在于一个或多个容器中，由docker挂载到容器，但不属于联合文件系统

卷的设计目的就是数据的持久化，完全独立于容器的生存周期，Docker不会在容器删除时删除其挂载的数据卷

特点：

```
1：数据卷可在容器之间共享或重用数据
2：卷中的更改可以直接生效
3：数据卷中的更改不会包含在镜像的更新中
```





##### 添加数据卷

直接添加

```shell
## 命令
docker run -it -v /宿主机绝对路径目录:/容器内目录      镜像名
## 查看数据卷是否挂载成功
docker inspect 容器ID

```

DockerFile添加

```shell
## 根目录下新建mydocker文件夹并进入
## 可在Dockerfile中使用VOLUME指令来给镜像添加一个或多个数据卷
出于可移植和分享的考虑，用-v 主机目录:容器目录这种方法不能够直接在Dockerfile中实现。
由于宿主机目录是依赖于特定宿主机的，并不能够保证在所有的宿主机上都存在这样的特定目录。
VOLUME["/dataVolumeContainer","/dataVolumeContainer2","/dataVolumeContainer3"]
## File构建
# volume test
FROM centos
VOLUME ["/dataVolumeContainer1","/dataVolumeContainer2"]
CMD echo "finished,--------success1"
CMD /bin/bash
## build后生成镜像
docker build /docker/DockerFile -t zzyy/centos .
获得一个新镜像zzyy/centos
## run容器
```

ps：Docker挂载主机目录Docker访问出现cannot open directory .: Permission denied
解决办法：在挂载目录后多加一个--privileged=true参数即可





#### 数据卷容器

##### 是什么？

命名的容器挂载数据卷，其它容器通过挂载这个(父容器)实现数据共享，挂载数据卷的容器，称之为数据卷容器