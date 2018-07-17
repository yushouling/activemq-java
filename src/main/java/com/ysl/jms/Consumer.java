package com.ysl.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
/**
 * 消费者
 */
public class Consumer {

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
        // 6.创建消息消费者MessageConsumer监听消息
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    System.out.println("接收消息：" + text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // 注意此处关闭连接将导致监听不到消息，因为是异步的
//        connection.close();
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
