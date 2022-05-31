package com.message.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author boreas
 * @create 2022-05-31 17:01
 */
public class ActiveMQConsumer {

    public static void main(String[] args) throws Exception {
        //创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //创建会话
        /** 第一个参数，是否使用事务
         如果设置true，操作消息队列后，必须使用 session.commit();
         如果设置false，操作消息队列后，不使用session.commit();
         */
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //创建队列
        Queue queue = session.createQueue("hello20181119");
        //创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
        while(true){
            //失效时间，如果10秒内没有收到新的消息，说明没有消息存在，此时可以退出当前循环
            TextMessage message = (TextMessage) consumer.receive(10000);
            if(message!=null){
                System.out.println(message.getText());
            }else {
                break;
            }
        }

        //关闭连接
        session.commit();
        session.close();
        connection.close();

        System.out.println("消费结束0");
    }
}
