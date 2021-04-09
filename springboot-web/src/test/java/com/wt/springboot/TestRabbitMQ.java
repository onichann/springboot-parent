package com.wt.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wutong
 * @date 2021/4/8 17:10
 * introduction
 */
@SpringBootTest(classes = SpringbootWebApplication.class)
@RunWith(SpringRunner.class)
public class TestRabbitMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //topic 动态路由 订阅模式
    @Test
    public void testTopic() {
        rabbitTemplate.convertAndSend("topics", "user.save", "user.save 路由消息");
    }

    //route  路由模式
    @Test
    public void testRoute() {
        rabbitTemplate.convertAndSend("directs", "info", "发送info的key的路由信息");
    }

    //fanout 广播
    @Test
    public void testFanout() {
        rabbitTemplate.convertAndSend("logs", "", "Fanout的模型发送消息");
    }

    //work
    @Test
    public void testWork() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", "work模型");
        }
    }

    //hello world
    @Test
    public void testHello() {
        rabbitTemplate.convertAndSend("hello", "hello world");
    }
}
