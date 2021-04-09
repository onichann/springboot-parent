package com.wt.springboot.rabbitmq;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wutong
 * @date 2021/4/8 17:35
 * introduction
 */
@Component
public class WorkConsumer {

    //一个消费者
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receive1(String message) {
        System.out.println("message1=" + message);
    }

    //一个消费者
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receive2(String message) {
        System.out.println("message1=" + message);
    }
}
