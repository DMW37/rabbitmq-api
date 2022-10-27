package com.study.test.rabbitmq.utils;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: 邓明维
 * @date: 2022/10/27
 * @description: 负责创建RabbitMQ的连接
 */
public class RabbitMQConnectionUtil {
    public static final String RABBITMQ_HOST = "192.168.72.130";
    public static final Integer RABBITMQ_PORT = 5672;
    public static final String RABBITMQ_USERNAME = "admin";
    public static final String RABBITMQ_PASSWORD = "admin";
    public static final String RABBITMQ_VIRTUAL_HOST = "/";

    public static Connection getConnection()  {
        // 1.创建ConnectionFactory工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2.设置工厂属性
        factory.setHost(RABBITMQ_HOST);
        factory.setPort(RABBITMQ_PORT);
        factory.setUsername(RABBITMQ_USERNAME);
        factory.setPassword(RABBITMQ_PASSWORD);
        factory.setVirtualHost(RABBITMQ_VIRTUAL_HOST);
        // 3.创建连接对象
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) throws Exception {
        Connection connection = getConnection();
        System.out.println(connection);
    }
}
