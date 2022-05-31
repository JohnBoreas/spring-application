- 标准：-开头，所有HotSpot都支持
- 非标准：-X开头，特点版本HotSpot支持特定的命令
- 不稳定：-XX开头，下个版本可能会取消的命令





```java
// 查看当前所用jdk的一些配置参数默认值
java -XX:+PrintFlagsFinal
// 查看默认的GC
java -XX:+PrintCommandLineFlags -version ~/Xxx.class
// 打印heap的概要信息，GC使用的算法，heap的配置及使用情况
jmap -heap 8633
// 显示堆中对象的统计信息， 会强制一次full gc
jmap -histo:live 11869 | more 
```



部分GC参数

```java
-Xms2688M // 设置初始堆的大小
-Xmx512m // 设置最大堆的大小
-Xmn960M // 设置年轻代 (新生代) 初始和最大堆大小
-Xss32M // 设置 Java 线程堆栈大小
-XX:MetaspaceSize=512M // 设置初始元数据区大小
-XX:MaxDirectMemorySize // 设置最大堆外内存大小

-XX:+UseG1GC // 使用G1回收器
-XX:MaxGCPauseMillis=100 // gc的清理间隔时间
-XX:InitiatingHeapOccupancyPercent=35 // 启动G1的堆空间占用比例
-verbose:gc // 类加载详细过程

-XX:+PrintGCDetails // 打印GC日志详情, 包含堆上各个代在GC前后的大小信息
-XX:+PrintGCDateStamps // 打印gc启动时间的相对时间
-XX:+PrintGCTimeStamps // 打印发送GC的时间
-XX:+PrintGCApplicationStoppedTime // 打印GC的stw停止时间

-Xloggc:${BASE_DIR}/logs/jvm_gc.log // 打印GClog
-XX:ErrorFile=${BASE_DIR}/logs/jvm_err.log // 打印Gc报错log
-XX:+HeapDumpOnOutOfMemoryError // 导出oom异常对dump异常快照
-XX:HeapDumpPath=${BASE_DIR}/logs/jvm_dump_pid%p.hprof 
```

