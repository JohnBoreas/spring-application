一、功能

实时查看和调整虚拟机各项参数。



二、命令

jinfo命令格式： 

```shell
jinfo [ option ] pid 

// 查询CMSInitiatingOccupancyFraction参数值
$ jinfo -flag CMSInitiatingOccupancyFraction 1444 
-XX:CMSInitiatingOccupancyFraction=85
```

