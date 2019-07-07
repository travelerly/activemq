package com.colin.activemq.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import sun.tools.java.ClassPath;

import javax.jms.Message;
import javax.jms.TextMessage;

@Service
public class SpringMQ_Producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Producer producer = (SpringMQ_Producer) ctx.getBean("springMQ_Producer");

        producer.jmsTemplate.send((session) -> {
            TextMessage textMessage = session.createTextMessage("===spring和activemq整合case===");
            return  textMessage;
        });

        System.out.println("===send task over===");
    }
}

