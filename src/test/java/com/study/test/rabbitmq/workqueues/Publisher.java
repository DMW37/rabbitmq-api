package com.study.test.rabbitmq.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.test.rabbitmq.utils.RabbitMQConnectionUtil;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author: 邓明维
 * @date: 2022/10/27
 * @description: 消息发送方
 */
public class Publisher {
    private static final String QUEUE_NAME = "work queues";
    @Test
    public void publish() throws Exception{
        // 1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();
        // 2.Channel
        Channel channel = connection.createChannel();
        // 3.构建队列:队列名称，是否持久化，是否独占，是否删除，参数
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 4.发布消息
        for (int i = 0; i < 10; i++) {
            String message = "work queue "+ i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes(StandardCharsets.UTF_8));
        }
        System.out.println("消息发送成功!");

    }
}
