package com.colin.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_TX {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "queue_01";

    public static void main(String[] args) throws JMSException, IOException {
        //1.创建连接工厂,按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建回话session.第一个参数：事务；第二个参数：签收
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题）
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消息消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        /**
         * 同步阻塞方法receive，订阅或者接收者调用message.receive()方法来接收消息，
         * receive方法在能够接收到消息之前将一直阻塞。
         */
        while (true){
            TextMessage textMessage = (TextMessage)messageConsumer.receive(3000L);
            //TextMessage textMessage = (TextMessage)messageConsumer.receive(4000L);
            if(null != textMessage){
                System.out.println("消费者接收到消息：" + textMessage.getText());
                textMessage.acknowledge();
            }else {
                break;
            }
        }
        //关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
