package com.study.test.rabbitmq.publishandsubscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.test.rabbitmq.utils.RabbitMQConnectionUtil;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author: 邓明维
 * @date: 2022/10/27
 * @description: 发布订阅的消息发布者
 */
public class Publisher {
    private static final String EXCHANGE_NAME ="publisher/subscribe";
    private static final String QUEUE_NAME01 = "publish-one";
    private static final String QUEUE_NAME02 = "publish-two";

    @Test
    public void publish() throws Exception{
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明交换机的模式
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        // 交换机发布消息到指定的Queue名称
        channel.queueDeclare(QUEUE_NAME01,false,false,false,null);
        channel.queueDeclare(QUEUE_NAME02,false,false,false,null);
        // 将交换机与队列绑定
        channel.queueBind(QUEUE_NAME01,EXCHANGE_NAME,"");
        channel.queueBind(QUEUE_NAME02,EXCHANGE_NAME,"");
        // 发送消息
        channel.basicPublish(EXCHANGE_NAME,"fanout时routingKey可以随便写",null,EXCHANGE_NAME.getBytes(StandardCharsets.UTF_8));
        System.out.println("发送消息成功!");
    }
}
