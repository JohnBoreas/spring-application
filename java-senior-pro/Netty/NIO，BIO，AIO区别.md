#### BIO

阻塞IO



一个线程负责连接，多线程则为每一个接入开启一个线程

一个请求一个应答

请求之后应答之前客户端会一直等待（阻塞）



#### NIO

同步非阻塞IO

![](..\resource\NIO模型.png)

NIO的三大核心部分：

Channel（通道）：和IO中的Stream是差不多一个等级的，只不过Stream是单向的，而Channel

是双向的，既可以用来读操作，也可以用来写操作

Buffer（缓冲区）：实际上是一个容器，一个连续数组，Channel提供从文件、网络读取数据的渠

道，但是读写的数据都必须经过Buffer

Selector（复用器）：将Channel注册到Selector上，Channel必须处于非阻塞模式下

**（****channel.configureBlocking(false)****）**



#### AIO

异步非阻塞IO