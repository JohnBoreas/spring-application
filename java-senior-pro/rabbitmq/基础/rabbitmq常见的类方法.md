1、ConnectionFactory、Connection、Channel

```
ConnectionFactory、Connection、Channel都是RabbitMQ对外提供的API中最基本的对象。

Connection是RabbitMQ的socket链接，它封装了socket协议相关部分逻辑。

ConnectionFactory为Connection的制造工厂。

Channel是我们与RabbitMQ打交道的最重要的一个接口，我们大部分的业务操作是在Channel这个接口中完成的，包括定义Queue、定义Exchange、绑定Queue与Exchange、发布消息等。
```

2、