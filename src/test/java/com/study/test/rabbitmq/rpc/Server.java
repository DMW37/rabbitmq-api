package com.study.test.rabbitmq.rpc;

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
public class Server {
    public static final String QUEUE_CLIENT = "rpc_client";
    public static final String QUEUE_SERVER = "rpc_server";

    @Test
    public void server() throws Exception {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_CLIENT, false, false, false, null);
        channel.queueDeclare(QUEUE_SERVER, false, false, false, null);
        // 监听消息
        channel.basicConsume(QUEUE_SERVER, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("server获取到的消息:" + new String(body, StandardCharsets.UTF_8));
                String resp = "响应信息";
                String respQueueName = properties.getReplyTo();
                String correlationId = properties.getCorrelationId();
                System.out.println(respQueueName + " : " + correlationId);
                AMQP.BasicProperties props = new AMQP.BasicProperties().builder().correlationId(correlationId).build();
                channel.basicPublish("", respQueueName, props, resp.getBytes(StandardCharsets.UTF_8));
                channel.basicAck(envelope.getDeliveryTag(), false);

            }
        });
        System.out.println("开始监听队列");
        System.in.read();
    }
}
