package com.message.rabbitmq;

import com.message.rabbitmq.service.DataProcess;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author boreas
 * @create 2019-12-18 15:18
 */
public class RabbitMqComsumer<T> extends DefaultConsumer {

    private static Logger logger = Logger.getLogger(RabbitMqComsumer.class);

    private DataProcess processService;

    private Channel channel;

    public RabbitMqComsumer(Channel channel, DataProcess<T> processService) {
        super(channel);
        this.channel = channel;
        this.processService = processService;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        if (body != null) {
            String jsonStr = new String(body);
            try {
                processService.doInProcess(jsonStr);
                /**
                 * 消息处理完成，手动确认提交
                 * deliveryTag 该消息的index
                 * multiple：是否批量 true:将一次性ack所有小于deliveryTag的消息。
                 */
                channel.basicAck(envelope.getDeliveryTag(), false);
            } catch (Exception e) {
                logger.error("error body:" + jsonStr, e);
            }
        }
    }
}
