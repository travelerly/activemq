package com.colin.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_Topic_Persist {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String TOPIC_NAME = "topic_persist";

    public static void main(String[] args) throws JMSException {


        //1.创建连接工厂,按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection
        Connection connection = activeMQConnectionFactory.createConnection();

        //3.创建回话session.第一个参数：事务；第二个参数：签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（主题）
        Topic topic = session.createTopic(TOPIC_NAME);
        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);
        //设置持久化模式
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        connection.start();
        //6.使用消息生产者messageProducer生产3条消息发送到MQ主题中
        for (int i = 1; i <= 3; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage(TOPIC_NAME + " 第 " + i + " 条消息");
            //8.通过消息生产者将消息返送给MQ
            messageProducer.send(textMessage);
        }
        //9.关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("======="+TOPIC_NAME+"消息发布到MQ完成======");
    }
}
