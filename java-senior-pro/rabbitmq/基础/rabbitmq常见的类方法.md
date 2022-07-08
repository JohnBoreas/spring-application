1、ConnectionFactory、Connection、Channel

```
ConnectionFactory、Connection、Channel都是RabbitMQ对外提供的API中最基本的对象。

Connection是RabbitMQ的socket链接，它封装了socket协议相关部分逻辑。

ConnectionFactory为Connection的制造工厂。

Channel是我们与RabbitMQ打交道的最重要的一个接口，我们大部分的业务操作是在Channel这个接口中完成的，包括定义Queue、定义Exchange、绑定Queue与Exchange、发布消息等。
```



2、交换机与队列的创建绑定

```java
// 创建通道
Channel channel = connection.createChannel();
// 声明exchange，指定类型为direct
channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType, true, false, false, new HashMap());
// 声明队列
// 队列，是否定义持久化队列，是否独占本次连接，是否在不使用的时候自动删除队列，队列其它参数
channel.queueDeclare(QUEUE_NAME, true, false, false, null);
// 绑定交换机
// 交换机名称，路由key,简单模式可以传递队列名称, 消息其它属性, 消息内容
channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
//发送消息
channel.basicPublish(EXCHANGE_NAME, 路由key or QUEUE_NAME, properties, message);
```



confirm模式

```java
//开启confirm模式
channel.confirmSelect();
//设置监听器,异步confirm模式
channel.addConfirmListener(new ConfirmListener() {
    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("ack:deliveryTag:"+deliveryTag+"，multiple:"+multiple);
    }
    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("nack:deliveryTag:"+deliveryTag+"，multiple:"+multiple);
    }
});
//持久化属性配置
AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
        .deliveryMode(2) // 2代表持久化，其他代表瞬态，rabbitmq重启就没了
        .build();
//发送消息
channel.basicPublish(EXCHANGE_NAME, "",properties, message);
// 批量confirm模式
if (channel.waitForConfirms()) {  //channel confirm后producer调用
    System.out.println("发送成功");
} else {
    System.out.println("发送失败");
}
```

tx模式

```java
channel.queueDeclare(QUEUE_NAME, true, false, false, null);
// 要发送的信息
String message = "你好；小兔子！";
channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
try {
    //开启txSelect事务
    channel.txSelect();
    for (int i = 0; i < 10; i++) {
        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder();
        properties.deliveryMode(2);  // 设置消息是否持久化，1： 非持久化 2：持久化
        //设置mandatory为true
        channel.basicPublish(EXCHANGE_NAME, "",properties.build(), message.getBytes());
    }
    //提交
    channel.txCommit();
}catch (Exception e){
    //回滚
    channel.txRollback();
}
```



备用交换机

```java
// 声明交换机
Map<String,Object> arguments = new HashMap<String,Object>();
// 指定交换机的备份交换机
arguments.put("alternate-exchange","ALTERNATE_EXCHANGE1");
// 绑定备份交换机参数arguments
channel.exchangeDeclare("simple_exchange_alter", BuiltinExchangeType.DIRECT, true, false
        , false, arguments);
// 声明备份exchange，指定类型为fanout
channel.exchangeDeclare("ALTERNATE_EXCHANGE1", BuiltinExchangeType.FANOUT);
channel.exchangeDeclare("ALTERNATE_EXCHANGE2", BuiltinExchangeType.FANOUT);

// 声明（创建）队列
channel.queueDeclare(QUEUE_NAME1, true, false, false, null);
channel.queueDeclare(QUEUE_NAME2, true, false, false, null);

// 要发送的信息
String message = "你好；小兔子！";
// 目标交换机绑定队列
channel.queueBind(QUEUE_NAME1,"simple_exchange_alter","");
channel.queueBind(QUEUE_NAME2,"ALTERNATE_EXCHANGE2","");
// 备份交换机绑定队列
channel.queueBind(QUEUE_NAME1,"ALTERNATE_EXCHANGE1","");
// 修改routing_key进行备份测试
channel.basicPublish("simple_exchange_alter", "1",null, message.getBytes());
```