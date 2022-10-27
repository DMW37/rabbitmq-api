package com.study.test.rabbitmq.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.test.rabbitmq.utils.RabbitMQConnectionUtil;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author: 邓明维
 * @date: 2022/10/27
 * @description: 主题匹配方式
 */
public class Publisher {
    private static final String EXCHANGE_NAME="topic";
    private static final String QUEUE_NAME01 ="topic-one";
    private static final String QUEUE_NAME02 ="topic-two";

    @Test
    public void publish() throws Exception{
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(QUEUE_NAME01,false,false,false,null);
        // topic 方式 exchange 绑定 queue
        // # 1个或多个单词
        // * 恰好一个单词
        channel.queueBind(QUEUE_NAME01,EXCHANGE_NAME,"*.RANGE.*");
        channel.queueBind(QUEUE_NAME02,EXCHANGE_NAME,"*.*.rabbit");
        channel.queueBind(QUEUE_NAME02,EXCHANGE_NAME,"lazy.#");
        // 发送消息
        channel.basicPublish(EXCHANGE_NAME,"路由key要符合表达式",null,"xxx".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(EXCHANGE_NAME,"big.RANGE.rabbit",null,"兔子".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(EXCHANGE_NAME,"big.xx.rabbit",null,"肚子".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(EXCHANGE_NAME,"lazy.dog.goe.goe",null,"狗子".getBytes(StandardCharsets.UTF_8));
        System.out.println("消息发送成功!");
    }
}
