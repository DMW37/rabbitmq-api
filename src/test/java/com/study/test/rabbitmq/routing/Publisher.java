package com.study.test.rabbitmq.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.test.rabbitmq.utils.RabbitMQConnectionUtil;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author: 邓明维
 * @date: 2022/10/27
 * @description: 路由的生产者
 */
public class Publisher {
    private static final String EXCHANGE_NAME="routing";
    private static final String QUEUE_NAME1 = "routing-one";
    private static final String QUEUE_NAME2 = "routing-two";

    @Test
    public void publish() throws Exception{
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 构建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(QUEUE_NAME1,false,false, false,null);
        channel.queueDeclare(QUEUE_NAME2,false,false, false,null);
        // 绑定
        channel.queueBind(QUEUE_NAME1,EXCHANGE_NAME,"ORANGE");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"BLACK");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"GREEN");
        // 发送消息
        channel.basicPublish(EXCHANGE_NAME,"routingKey要和绑定时一致，不然发送不会成功",null,"sss".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(EXCHANGE_NAME,"ORANGE",null,"橘黄色".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(EXCHANGE_NAME,"GREEN",null,"绿色".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(EXCHANGE_NAME,"BLACK",null,"黑色".getBytes(StandardCharsets.UTF_8));
        System.out.println("消息发送成功!");
    }
}
