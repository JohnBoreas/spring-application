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



