package com.message.rabbitmq.worker;

import com.message.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author boreas
 * @create 2020-03-05 下午 11:01
 */
public class ConsumerOne {

    private final static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {
        //1、获取连接
        Connection connection = ConnectionUtil.getConnection();
        //2、声明通道
        Channel channel = connection.createChannel();
        //3、声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //同一时刻服务器只会发送一条消息给消费者
        //channel.basicQos(1);

        //4、定义队列的消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                //6、获取消息
                String message = new String(body);
                System.out.println(" [x] Received '" + message + "'");
                //消费者1接收一条消息后休眠10毫秒
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //返回确认状态
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //5、监听队列,手动返回完成状态
        channel.basicConsume(QUEUE_NAME, false, consumer);

    }
}
