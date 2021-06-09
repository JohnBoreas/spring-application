统计

```shell

```

cpu、内存性能查看

```shell
top
```

Java查看

```shell
ps -ef|grep 'tomcat'
## 进入java安装目录或者命令前面加路径
jstack -l pid
```

赋予权限

```shell
sudo chmod -R 777 文件
```



如何在 Windows 和 Linux 上查找哪个线程cpu利用率最高？

windows上面用任务管理器看，linux下可以用 top 这个工具看。

1、找出cpu耗用厉害的进程pid， 终端执行top命令，然后按下shift+p 查找出cpu利用最厉害的pid号

2、根据上面第一步拿到的pid号，top -H -p pid 。然后按下shift+p，查找出cpu利用率最厉害的线程号，比如top -H -p 1328

3、将获取到的线程号转换成16进制，去百度转换一下就行

4、使用jstack工具将进程信息打印输出，jstack pid号 > /tmp/t.dat，比如jstack 31365 > /tmp/t.dat

5、编辑/tmp/t.dat文件，查找线程号对应的信息