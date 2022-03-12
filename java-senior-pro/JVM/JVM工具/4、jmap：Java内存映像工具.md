一、功能

用于生成堆转储快照（一般称为heapdump或dump文件）



二、命令

jmap命令格式为： 

```powershell
jmap [ option ] vmid

$ jmap -dump:format=b,file=eclipse.bin 3500 
Dumping heap to C:\Users\IcyFenix\eclipse.bin ... 
Heap dump file created


## 显示堆中对象的统计信息， 会强制一次full gc
jmap -histo:live 11869 | more 
```

num      #instances      #bytes       class name

序号      实例个数           字节数       类名

class name对应的就是Class文件里的class的标识
B代表byte、C代表char、D代表double
F代表float、I代表int、J代表long、Z代表boolean
前边有[代表数组，[I 就相当于int[]



三、工具主要选项

![](../../resource/jmap工具主要选项.png)



四、1.8以后版本查看

```shell
jhsdb jmap --heap --pid 16279

```

