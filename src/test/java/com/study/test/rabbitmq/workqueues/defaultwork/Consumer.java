package com.study.test.rabbitmq.workqueues.defaultwork;

import com.rabbitmq.client.*;
import com.study.test.rabbitmq.utils.RabbitMQConnectionUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: 邓明维
 * @date: 2022/10/27
 * @description: work queues消费端
 */
public class Consumer {
    private static final String QUEUE_NAME = "work queues";

    @Test
    public void consume01() throws Exception {
        // 1.创建连接
        Connection connection = RabbitMQConnectionUtil.getConnection();
        // 2.Channel
        Channel channel = connection.createChannel();
        // 3.Queue : 队列名称，是否持久，是否独占，是否删除，参数
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 4.消息监听
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("work queue consumer01 get message:" + new String(body, StandardCharsets.UTF_8));
            }
        };
        channel.basicConsume(QUEUE_NAME,true,consumer);
        System.in.read();
    }

    @Test
    public void consume02() throws Exception {
        // 1.创建连接
        Connection connection = RabbitMQConnectionUtil.getConnection();
        // 2.Channel
        Channel channel = connection.createChannel();
        // 3.Queue : 队列名称，是否持久，是否独占，是否删除，参数
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 4.消息监听
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("work queue consumer02 get message:" + new String(body, StandardCharsets.UTF_8));
            }
        };
        channel.basicConsume(QUEUE_NAME,true,consumer);
        System.in.read();
    }
}
