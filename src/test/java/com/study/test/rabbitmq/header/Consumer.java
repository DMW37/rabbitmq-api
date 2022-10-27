package com.study.test.rabbitmq.header;

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
    private static final String HEADER_QUEUE_All = "header-queue-all";
    private static final String HEADER_QUEUE_ANY = "header-queue-any";
    @Test
    public void consume01() throws Exception{
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(HEADER_QUEUE_All,false,false,false,null);
        com.rabbitmq.client.Consumer consumer =new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consume01 queue one get message :"+new String(body, StandardCharsets.UTF_8));
            }
        };
        channel.basicConsume(HEADER_QUEUE_All,true,consumer);
        System.in.read();
    }

    @Test
    public void consume02() throws Exception{
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(HEADER_QUEUE_ANY,false,false,false,null);
        com.rabbitmq.client.Consumer consumer =new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consume02 queue two get message :"+new String(body, StandardCharsets.UTF_8));
            }
        };
        channel.basicConsume(HEADER_QUEUE_ANY,true,consumer);
        System.in.read();
    }
}
