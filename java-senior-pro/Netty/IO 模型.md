在Linux中，将I/O模型分为五种类型：

```
阻塞式I/O模型

非阻塞式I/O模型

I/O复用模型

信号驱动式 I/O 模型

异步 I/O模型
```





Linux中的 I/O 流程概括来说可以分为两步：

1、等待数据准备好（waiting for the to be ready） 

2、从内核向进程复制数据(copying the data from the kernel to the process)



阻塞式IO模型

<img src="..\resource\阻塞式IO模型.png" style="zoom:87%;" />

非阻塞IO模型

<img src="..\resource\非阻塞IO模型.png" style="zoom:87%;" />