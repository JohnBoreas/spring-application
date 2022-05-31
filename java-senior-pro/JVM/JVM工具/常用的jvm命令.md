查看jvm的垃圾回收器

```shell
java -XX:+PrintCommandLineFlags -version

top    ## 找出cpu占用高的程序的pid
top -Hp pid ## 查具体线程
ps -mp pid -o THREAD,tid,time  ## 找出进程下的线程
printf "%x\n" tid  ## 转化tid为16进制
jstack pid |grep tid -A 30
```



CPU高

（1）原因：

- Full GC次数过多；
- 代码中有比较耗CPU的操作，比较耗时的计算，导致CPU过高；

（2）定位是哪个线程高

Top –Hp PID

Printf ‘%x\n’ PID

（3）查看线程的堆栈信息

Jstack PID | grep tid –A line

（4）观察GC次数

jstat -gcutil 9 1000 10

（5）解决：

调整xms与xmx大小



```
# 第一步先top 找出 哪个java进程占用率最高
top
#显示如下
PID USER      PR  NI    VIRT    RES    SHR S  %CPU %MEM     TIME+ COMMAND     
24525 root      20   0 4909144 1.421g   6248 S   6.7 18.6  67:52.53 jsvc
# 第二步 使用top -Hp Pid 找出这个pid进程里哪个线程占用率最高
top -Hp 24525 
显然如下：
PID USER      PR  NI    VIRT    RES    SHR S %CPU %MEM     TIME+ COMMAND  
24624 root      20   0 4917660 1.427g  14548 S  2.7 18.7   0:30.39 jsvc 
# 第三步使用jstack查找改进程的堆栈信息，需要把top -Hp的PID转换成16进制
printf '%x\n' 24624 输出：66030
# 使用jstack -l pid（进程的）|grep 16进制（top- Hp线程PID）查找堆栈
jstack -l 24525 |grep 66030
输出堆栈信息，结合源码进行原因查找
使用jmap分析java进程的内存占用分析
使用jmap会导致系统STW
jmap -histo 进程ID |head 20
jmap -dump:format=b,file=xxx pid /jmap -histo
```



**工具：阿里arthas工具使用**

```
java -jar arthas-boot.jar
按数组选择java进程

# 常用命令有
1. dashboard ：类似top命令实时检控线程情况
2. jvm ：把当前java进程的jvm配置全部显示出来（栈，堆内存等等info）
3. thread 可以跟个线程ID：查看所有线程列表信息，后面跟着线程ID，
4. heapdump : 导出堆内存情况，会导致stw使用jhat -J-mx512M xxx.hprof
5. redefine 热替换，线上直接替换文件
```