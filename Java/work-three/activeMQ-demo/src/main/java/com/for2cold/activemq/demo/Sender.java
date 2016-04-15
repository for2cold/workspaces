package com.for2cold.activemq.demo;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by jasme on 2016/4/15 16:09.
 */
public class Sender {

    private static final int SEND_NUMBER = 5;

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    public static void main(String[] args) {

        // ConnectionFactory: 连接工厂，JMS 用它创建连接
        ConnectionFactory connectionFactory = null;
        // Connection: JMS 客户端到JMS Provider的连接
        Connection connection = null;
        // Session：一个发送货接收消息的线程
        Session session = null;
        // Destination：消息目的地，消息发送给谁
        Destination destination = null;
        // MessageProducer：消息发送者
        MessageProducer producer = null;
        // TextMessage message
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://0.0.0.0:61616"
        );

        // 构造从工厂得到连接对象
        try {
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue("FirstQueue");

            producer = session.createProducer(destination);

            // 设置不持久化
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            for (int i = 1; i <= SEND_NUMBER; ++i) {
                TextMessage msg = session.createTextMessage("ActiveMQ 发送消息：" + i);
                LOGGER.info("发送消息：[ActiveMQ 发送消息：" + i + "]");
                System.out.println("发送消息：[ActiveMQ 发送消息：" + i + "]");
                producer.send(msg);
            }

            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                }
            }
        }
    }
}
