Watcher 特性：

![](..\resource\watcher监听.png)



如何注册事件机制：

ZooKeeper 的 Watcher 机制，总的来说可以分为三个过程：

**客户端注册** **Watcher** 、**服务器处理Watcher** **和客户端回调** **Watcher** 。



**注册 watcher 有 3 种方式**： **getData、exists**、 **getChildren**；



ZK的所有读操作都都可以设置watch监视点: getData, getChildren, exists. 写操作则是不能设置监视点的。



可能监测的事件类型有: **None,NodeCreated, NodeDataChanged, NodeDeleted,** **NodeChildrenChanged**.

```
None // 客户端连接状态发生变化的时候 会收到None事件 

NodeCreated // 节点创建事件 

NodeDeleted // 节点删除事件 

NodeDataChanged // 节点数据变化 

NodeChildrenChanged // 子节点被创建 删除触发该事件
```



ZK 可以做到，只要数据一发生变化，就会通知相应地注册了监听的客户端。那么，它是怎么做到的呢？

其实原理应该是很简单的，四个步骤：

1、客户端注册Watcher到服务端; 

2、服务端发生数据变更;

3、服务端通知客户端数据变更;

4、客户端回调Watcher处理变更应对逻辑; 



注册 watcher 监听事件流程图：

![](..\resource\watcher源码分析.png)



Zookeeper允许客户端向服务端的某个Znode注册一个Watcher监听，当服务端的一些指定事件触发了这个Watcher，服务端会向指定客户端发送一个事件通知来实现分布式的通知功能，然后客户端根据Watcher通知状态和事件类型做出业务上的改变。



Watcher特性总结：

1. 一次性
   无论是服务端还是客户端，一旦一个Watcher被触发，Zookeeper都会将其从相应的存储中移除。这样的设计有效的减轻了服务端的压力，不然对于更新非常频繁的节点，服务端会不断的向客户端发送事件通知，无论对于网络还是服务端的压力都非常大。

2. 客户端串行执行
   客户端Watcher回调的过程是一个串行同步的过程。

3. 轻量

   1）Watcher通知非常简单，只会告诉客户端发生了事件，而不会说明事件的具体内容。

   2）客户端向服务端注册Watcher的时候，并不会把客户端真实的Watcher对象实体传递到服务端，仅仅是在客户端请求中使用boolean类型属性进行了标记。

4. watcher event异步发送watcher的通知事件从server发送到client是异步的，这就存在一个问题，不同的客户端和服务器之间通过socket进行通信，由于网络延迟或其他因素导致客户端在不通的时刻监听到事件，由于Zookeeper本身提供了ordering guarantee，即客户端监听事件后，才会感知它所监视znode发生了变化。所以我们使用Zookeeper不能期望能够监控到节点每次的变化。Zookeeper只能保证最终的一致性，而无法保证强一致性。

5. 注册watcher getData、exists、getChildren

6. 触发watcher create、delete、setData

7. 当一个客户端连接到一个新的服务器上时，watch将会被以任意会话事件触发。当与一个服务器失去连接的时候，是无法接收到watch的。而当client重新连接时，如果需要的话，所有先前注册过的watch，都会被重新注册。通常这是完全透明的。只有在一个特殊情况下，watch可能会丢失：对于一个未创建的znode的exist watch，如果在客户端断开连接期间被创建了，并且随后在客户端连接上之前又删除了，这种情况下，这个watch事件可能会被丢失。





一、客户端注册Watcher实现

- 调用getData()/getChildren()/exist()三个API，传入Watcher对象
- 标记请求request，封装Watcher到WatchRegistration
- 封装成Packet对象，发服务端发送request
- 收到服务端响应后，将Watcher注册到ZKWatcherManager中进行管理
- 请求返回，完成注册。



二、服务端处理Watcher实现

服务端接收Watcher并存储
　　接收到客户端请求，处理请求判断是否需要注册Watcher，需要的话将数据节点的节点路径和ServerCnxn（ServerCnxn代表一个客户端和服务端的连接，实现了Watcher的process接口，此时可以看成一个Watcher对象）存储在WatcherManager的WatchTable和watch2Paths中去。



Watcher触发
　　以服务端接收到 setData() 事务请求触发NodeDataChanged事件为例：
封装WatchedEvent
　　将通知状态（SyncConnected）、事件类型（NodeDataChanged）以及节点路径封装成一个WatchedEvent对象

查询Watcher
　　从WatchTable中根据节点路径查找Watcher
　　没找到；说明没有客户端在该数据节点上注册过Watcher
　　找到；提取并从WatchTable和Watch2Paths中删除对应Watcher（从这里可以看出Watcher在服务端是一次性的，触发一次就失效了）
　　调用process方法来触发Watcher
　　这里process主要就是通过ServerCnxn对应的TCP连接发送Watcher事件通知。



三、客户端回调Watcher

客户端SendThread线程接收事件通知，交由EventThread线程回调Watcher。客户端的Watcher机制同样是一次性的，一旦被触发后，该Watcher就失效了。