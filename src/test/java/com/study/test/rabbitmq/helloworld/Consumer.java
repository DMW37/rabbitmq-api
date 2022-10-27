package com.study.test.rabbitmq.helloworld;

import com.rabbitmq.client.*;
import com.study.test.rabbitmq.utils.RabbitMQConnectionUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: 邓明维
 * @date: 2022/10/27
 * @description: 简单模式的消息消费者
 */
public class Consumer {
    private static final String QUEUE_NAME = "helloWorld";

    @Test
    public void consume() throws Exception {
        // 1.创建连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();
        // 2.构建通道
        Channel channel = connection.createChannel();
        // 3.构建队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 4.监听队列
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者获得的消息:" + new String(body, StandardCharsets.UTF_8));
            }
        };
        // 队列名称，自动回复，回调对象
        channel.basicConsume(QUEUE_NAME, true, consumer);
        System.in.read();
    }
}
