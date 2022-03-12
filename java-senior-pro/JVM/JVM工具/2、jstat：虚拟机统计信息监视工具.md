一、功能

监视虚拟机各种运行状态信息的命令行工具。

它可以显示本地或者远程[1]虚拟机进程中的类加载、内存、垃圾收集、即时编译等运行时数据



二、命令

jstat命令格式为： 

```powershell
jstat [ option vmid [interval[s|ms] [count]] ] 

$ jstat -gc 2764 250 20 // 每250 毫秒查询一次进程2764垃圾收集状况，共查询20次
Survivor0 Survivor1 Eden区 Old老年代   永久代   Young GC   Full GC  Full GC Time   GC Time
S0        S1        E      O         P       YGC  YGCT   FGC      FGCT           GCT 
0.00      0.00      6.20   41.42     47.20   16   0.105  3        0.472          0.577
```



三、jstat工具主要选项.png

<img src="../../resource/jstat工具主要选项.png" style="zoom:80%;" />





四、垃圾回收统计 jstat -gc 19570

```
S0C：第一个幸存区的大小
S1C：第二个幸存区的大小
S0U：第一个幸存区的使用大小
S1U：第二个幸存区的使用大小
EC：伊甸园区的大小
EU：伊甸园区的使用大小
OC：老年代大小
OU：老年代使用大小
MC：方法区大小
MU：方法区使用大小
CCSC:压缩类空间大小
CCSU:压缩类空间使用大小
YGC：年轻代垃圾回收次数
YGCT：年轻代垃圾回收消耗时间
FGC：老年代垃圾回收次数
FGCT：老年代垃圾回收消耗时间
GCT：垃圾回收消耗总时间
```

五、堆内存统计 jstat -gccapacity 19570

```
NGCMN：新生代最小容量
NGCMX：新生代最大容量
NGC：当前新生代容量
S0C：第一个幸存区大小
S1C：第二个幸存区的大小
EC：伊甸园区的大小
OGCMN：老年代最小容量
OGCMX：老年代最大容量
OGC：当前老年代大小
OC:当前老年代大小
MCMN:最小元数据容量
MCMX：最大元数据容量
MC：当前元数据空间大小
CCSMN：最小压缩类空间大小
CCSMX：最大压缩类空间大小
CCSC：当前压缩类空间大小
YGC：年轻代gc次数
FGC：老年代GC次数
```

