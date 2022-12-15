#### session

服务器否个网页的时候，会在服务器端的内存里开辟一块内存，这块内存就叫做session，

而这个内存是跟浏览器关联在一起的。这个浏览器指的是浏览器窗口，或者是浏览器的子窗口，

意思就是，只允许当前这个session对应的浏览器访问，就算是在同一个机器上新启的浏览器也是无法访问的。而另外一个浏览器也需要记录session的话，就会再启一个属于自己的session



#### cookie与session的异同点

<img src="../resource/cookie与session的异同点.png" style="zoom:80%;" />

#### 获取cookie的方法

![](../resource/获取cookie的方法.png)

#### cookie与seesion的缺点

![](../resource/cookie与seesion的缺点.png)

#### WebStorage

* WebStorage的目的是克服由cookie所带来的一些限制，当数据需要被严格控制在客户端时，不需要持续的将数据发回服务器。

* WebStorage两个主要目标：

（1）提供一种在cookie之外存储会话数据的路径。

（2）提供一种存储大量可以跨会话存在的数据的机制。

* HTML5的WebStorage提供了两种API：localStorage（本地存储）和sessionStorage（会话存储）。

<img src="../resource/localStorage和sessionStorage.png" style="zoom:80%;" />

##### 优点

![](../resource/WebStorage相对于cookie的优点.png)