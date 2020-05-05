package com.message.rabbitmq.rpc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * @author boreas
 * @create 2020-02-28 上午 12:21
 */
public class RPCClient implements AutoCloseable {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";

    public RPCClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();
        channel = connection.createChannel();
    }
    // 建立联系和渠道。
    // 调用方法发出实际的RPC请求。
    // 首先生成一个唯一的relatedId 编号并将其保存-使用者回调将使用该值来匹配适当的响应。
    // 为回复创建一个专用的排他队列并订阅。
    // 发布具有两个属性的请求消息： replyTo和correlationId。
    // 等到正确的响应到达。
    // 消费者交付处理是在单独的线程中进行的，因此在响应到达之前，需要一些东西来挂起主线程。使用BlockingQueue是一种可行的解决方案。在这里，我们将创建 容量设置为1的ArrayBlockingQueue，因为我们只需要等待一个响应即可。
    // 消费者的工作很简单，对于每一个消费的响应消息，它都会检查correlationId 是否为我们要寻找的消息。如果是这样，它将响应放入BlockingQueue。
    // 同时，主线程正在等待响应，以将其从BlockingQueue中获取。
    // 最后，我们将响应返回给用户。
    public static void main(String[] argv) {
        try (RPCClient fibonacciRpc = new RPCClient()) {
            for (int i = 0; i < 32; i++) {
                String i_str = Integer.toString(i);
                System.out.println(" [x] Requesting fib(" + i_str + ")");
                String response = fibonacciRpc.call(i_str);
                System.out.println(" [.] Got '" + response + "'");
            }
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String call(String message) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.offer(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {
        });

        String result = response.take();
        channel.basicCancel(ctag);
        return result;
    }

    public void close() throws IOException {
        connection.close();
    }
}
