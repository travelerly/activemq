package com.colin.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_Topic {
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String TOPIC_NAME = "topic_01";

    public static void main(String[] args) throws JMSException, IOException {
        //1.创建连接工厂,按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建回话session.第一个参数：事务；第二个参数：签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（主题）
        Topic topic = session.createTopic(TOPIC_NAME);
        //5.创建消息消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);

        /**
         * 通过监听的方式来消费消息
         * 异步非阻塞式（监听器）
         * 订阅者或者接收者通过messageConsumer.setMessageListener(MessageListener listener)注册一个消息监听器
         * 当消息到达之后，系统自动调用监听器的onMessage方法
         *
         * 使用lambda表达式
         */
        messageConsumer.setMessageListener((message) -> {
            if(null != message && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("消费者接收到Topic消息： "+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //防止处理过快,还没有来得及消费，资源就已经关闭
        System.in.read();
        //关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
