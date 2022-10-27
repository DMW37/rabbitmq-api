package com.study.test.rabbitmq.workqueues.qos;

import com.rabbitmq.client.*;
import com.study.test.rabbitmq.utils.RabbitMQConnectionUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: 邓明维
 * @date: 2022/10/27
 * @description: work queues -- qos
 */
public class Consumer {

    private static final String QUEUE_NAME = "work queues";

    @Test
    public void consume01() throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 监听之前的流量监控 // 必会接收多少请求，其余剩下的轮询
        channel.basicQos(9);
        // 监听消息
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("work queues consume01 get message :" + new String(body, StandardCharsets.UTF_8)+":"+envelope.getDeliveryTag());
                // 手动回复
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // 不自动回复
        channel.basicConsume(QUEUE_NAME,false,consumer);
        System.in.read();
    }

    @Test
    public void consume02() throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 监听消息前的流量监控
        channel.basicQos(1);
        // 监听消息
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("work queues consume02 get message :" + new String(body, StandardCharsets.UTF_8)+":"+envelope.getDeliveryTag());
                // 手动回复
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // 不自动回复
        channel.basicConsume(QUEUE_NAME,false,consumer);
        System.in.read();
    }
}
