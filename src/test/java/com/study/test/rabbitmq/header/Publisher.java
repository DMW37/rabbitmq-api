package com.study.test.rabbitmq.header;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.test.rabbitmq.utils.RabbitMQConnectionUtil;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author: 邓明维
 * @date: 2022/10/27
 * @description:
 */
public class Publisher {
    private static final String HEADER_EXCHANGE="header-exchange";
    private static final String HEADER_QUEUE_All = "header-queue-all";
    private static final String HEADER_QUEUE_ANY = "header-queue-any";
    @Test
    public void publish() throws Exception{
        Connection connection = RabbitMQConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(HEADER_EXCHANGE, BuiltinExchangeType.HEADERS);
        channel.queueDeclare(HEADER_QUEUE_All,false,false,false,null);
        channel.queueDeclare(HEADER_QUEUE_ANY,false,false,false,null);
        // 基础Header绑定交换机和队列
        HashMap<String, Object> args_all = new HashMap<>();
        // x-match : all 需要全部满足
        // x-match : any 只需要满足一个即可
        args_all.put("x-match","all");
        args_all.put("name","jack");
        args_all.put("age","123");

        HashMap<String, Object> args_any = new HashMap<>();
        // x-match : all 需要全部满足
        // x-match : any 只需要满足一个即可
        args_any.put("x-match","any");
        args_any.put("name","jack");
        args_any.put("age","123");
        // 绑定
        channel.queueBind(HEADER_QUEUE_All,HEADER_EXCHANGE,"",args_all);
        channel.queueBind(HEADER_QUEUE_ANY,HEADER_EXCHANGE,"",args_any);
        // 发送消息
        String message = "头部测试";
        HashMap<String, Object> headers_all = new HashMap<>();
        headers_all.put("name","jack");
        headers_all.put("age","123");

        HashMap<String, Object> headers_any = new HashMap<>();
        headers_any.put("name","jack");
        AMQP.BasicProperties props_all = new AMQP.BasicProperties().builder().headers(headers_all).build();
        AMQP.BasicProperties props_any = new AMQP.BasicProperties().builder().headers(headers_any).build();
        channel.basicPublish(HEADER_EXCHANGE,"",props_all,message.getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(HEADER_EXCHANGE,"",props_any,message.getBytes(StandardCharsets.UTF_8));
        System.out.println("发送成功!");
    }
}
