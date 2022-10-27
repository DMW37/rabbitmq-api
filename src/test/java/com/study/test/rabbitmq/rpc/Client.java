package com.study.test.rabbitmq.rpc;

import com.rabbitmq.client.*;
import com.study.test.rabbitmq.utils.RabbitMQConnectionUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author: 邓明维
 * @date: 2022/10/27
 * @description:
 */
public class Client {
    public static final String QUEUE_CLIENT = "rpc_client";
    public static final String QUEUE_SERVER = "rpc_server";

    @Test
    public void client() throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_CLIENT, false, false, false, null);
        channel.queueDeclare(QUEUE_SERVER, false, false, false, null);
        // 发布消息
        String message = "hello rpc";
        String uuid = UUID.randomUUID().toString();
        AMQP.BasicProperties props = new AMQP.BasicProperties().builder().replyTo(QUEUE_CLIENT).correlationId(uuid).build();
        channel.basicPublish("", QUEUE_SERVER, props, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("发送消息成功");
        System.out.println("开始监听");
        channel.basicConsume(QUEUE_CLIENT, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String correlationId = properties.getCorrelationId();
                System.out.println(correlationId);
                if (correlationId != null && correlationId.equals(uuid)) {
                    System.out.println("收到服务器响应:" + new String(body, StandardCharsets.UTF_8));
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
        System.in.read();


    }
}
