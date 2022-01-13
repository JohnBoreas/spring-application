一、功能

命令用于生成虚拟机当前时刻的线程快照（一般称为threaddump或者 javacore文件）。



定位线程出现长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间挂 起等，都是导致线程长时间停顿的常见原因。



二、命令

jstack命令格式为： 

```powershell
jstack [ option ] vmid
```



三、工具主要选项

<img src="../../resource/jstack工具主要选项.png" style="zoom:80%;" />