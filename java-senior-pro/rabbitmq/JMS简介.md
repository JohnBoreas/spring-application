JMS包括以下基本构件：

```
1、连接工厂，是客户用来创建连接的对象，ActiveMQ提供的是ActiveMQConnectionFactory；
2、连接connection；
3、会话session，是发送和接收消息的上下文，用于创建消息生产者，消息消费者，相比rocketMQ会话session是提供事务性的；
4、目的地destination，指定生产消息的目的地和消费消息的来源对象；
5、生产者，由会话创建的对象。
6、消费者，由会话创建的对象。
```

