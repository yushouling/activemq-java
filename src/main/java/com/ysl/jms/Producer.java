package com.ysl.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * 生产者
 */
public class Producer {

    public static void main(String[] args) throws JMSException {
        // 1.创建连接工厂ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConst.brokerURL);
        // 2.创建连接Connection
        Connection connection = connectionFactory.createConnection();
        // 3.启动连接
        connection.start();
        // 4.创建回话Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5.创建消息目的地Destination：queue或topic
        Destination destination = null;
//        destination = getQueue(session);
        destination = getTopic(session);
        // 6.创建消息生产者MessageProducer
        MessageProducer producer = session.createProducer(destination);
        for (int i = 0; i < 10; i++) {
            // 7.创建消息TextMessage
            TextMessage message = session.createTextMessage("hello " + i);
            // 8.发送消息
            producer.send(message);
            System.out.println("发送消息：" + message.getText());
        }

        // 9.关闭连接
        connection.close();
    }

    /**
     * 创建队列
     * @param session
     * @return
     * @throws JMSException
     */
    private static Destination getQueue(Session session) throws JMSException {
        Queue queue = session.createQueue(ActiveMQConst.queueName);
        return queue;
    }

    /**
     * 创建主题
     * @param session
     * @return
     * @throws JMSException
     */
    private static Destination getTopic(Session session) throws JMSException {
        Topic topic = session.createTopic(ActiveMQConst.topicName);
        return topic;
    }
}
