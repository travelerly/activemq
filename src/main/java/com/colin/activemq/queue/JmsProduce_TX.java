package com.colin.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_TX {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "queue_01";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂,按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建回话session.第一个参数：事务；第二个参数：签收
        //开启事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题）
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //6.使用消息生产者messageProducer生产3条消息发送到MQ队列中
        for (int i = 1; i <= 6; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("第 " + i + " 条消息");
            //8.通过消息生产者将消息返送给MQ
            messageProducer.send(textMessage);
        }
        //提交事务
        //session.commit();
        //9.关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("=======消息发布到MQ完成======");
    }
}
