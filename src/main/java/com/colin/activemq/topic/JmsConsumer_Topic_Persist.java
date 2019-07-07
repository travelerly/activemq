package com.colin.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_Topic_Persist {
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String TOPIC_NAME = "topic_persist";

    public static void main(String[] args) throws JMSException, IOException {
        //控制台标识订阅者名字
        System.out.println("=====topic_persist_01====");

        //1.创建连接工厂,按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("topic_persist_01");

        //3.创建回话session.第一个参数：事务；第二个参数：签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（主题）
        Topic topic = session.createTopic(TOPIC_NAME);
        //设置订阅主题
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "===remark===");
        connection.start();

        Message message = topicSubscriber.receive();
        while (null != message){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("消费者接收到Topic_Persist消息："+textMessage.getText());
            message = topicSubscriber.receive();
        }

        session.close();
        connection.close();
    }
}
