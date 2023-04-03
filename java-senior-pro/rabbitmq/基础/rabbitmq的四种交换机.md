**交换机的功能**

主要是接收消息并且转发到绑定的队列，交换机不存储消息，在启用ack模式后，交换机找不到队列会返回错误



**交换机有四种类型：**

```shell
Direct, topic, Headers and Fanout

## Direct：direct 类型的行为是"先匹配, 再投送". 即在绑定时设定一个 routing_key, 消息的routing_key 匹配时, 才会被交换器投送到绑定的队列中去.
Direct Exchange 是 RabbitMQ 默认的交换机模式，也是最简单的模式，根据key全文匹配去寻找队列

## Topic：按规则转发消息（最灵活）
主要是根据通配符。路由键必须是一串字符，用句号（.） 隔开，比如说 agreements.us，路由模式必须包含一个 星号（*），主要用于匹配路由键指定位置的一个单词

## Headers：设置 header attribute 参数类型的交换机
根据规则匹配, 相较于 direct 和 topic 固定地使用 routing_key , headers 则是一个自定义匹配规则的类型.
在队列与交换器绑定时, 会设定一组键值对规则, 消息中也包括一组键值对( headers 属性), 当这些键值对有一对, 或全部匹配时, 消息被投送到对应队列.

## Fanout：转发消息到所有绑定队列
消息广播的模式，不管路由键或者是路由模式，会把消息发给绑定给它的全部队列，如果配置了 routing_key 会被忽略。
```



交换机的创建

```java
channel.exchangeDeclare(TOPIC_EXCHAGE, BuiltinExchangeType.TOPIC);
// 发送信息
String message = "新增了商品。Topic模式；routing key 为 item.insert " ;
channel.basicPublish(TOPIC_EXCHAGE, "item.insert.a.test", null, message.getBytes());
System.out.println("已发送消息：" + message);

// 发送信息
message = "修改了商品。Topic模式；routing key 为 item.update" ;
channel.basicPublish(TOPIC_EXCHAGE, "item.update", null, message.getBytes());
System.out.println("已发送消息：" + message);

// 发送信息
message = "删除了商品。Topic模式；routing key 为 item.delete" ;
channel.basicPublish(TOPIC_EXCHAGE, "item.delete", null, message.getBytes());

```

