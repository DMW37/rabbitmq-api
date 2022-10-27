package com.study.test.rabbitmq.publishandsubscribe;

import com.rabbitmq.client.*;
import com.study.test.rabbitmq.utils.RabbitMQConnectionUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: 邓明维
 * @date: 2022/10/27
 * @description:
 */
public class Consumer {
    private static final String QUEUE_NAME01 = "publish-one";
    private static final String QUEUE_NAME02 = "publish-two";

    @Test
    public void consume01() throws Exception{
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME01,false,false,false,null);
        com.rabbitmq.client.Consumer consumer =new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consume01 queue one get message :"+new String(body, StandardCharsets.UTF_8));
            }
        };
        channel.basicConsume(QUEUE_NAME01,true,consumer);
        System.in.read();
    }

    @Test
    public void consume02() throws Exception{
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME02,false,false,false,null);
        com.rabbitmq.client.Consumer consumer =new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consume02 queue two get message :"+new String(body, StandardCharsets.UTF_8));
            }
        };
        channel.basicConsume(QUEUE_NAME02,true,consumer);
        System.in.read();
    }
}
