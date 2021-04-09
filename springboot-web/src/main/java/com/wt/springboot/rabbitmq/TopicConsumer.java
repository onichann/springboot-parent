package com.wt.springboot.rabbitmq;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wutong
 * @date 2021/4/9 10:49
 * introduction
 */
@Component //* 1个单词  #0个或多个
public class TopicConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(type = "topic", name = "topics"),
                    key = {"user.save", "user.*"}
            )
    })
    public void receive1(String message) {
        System.out.println("message1 =" + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(type = "topic", name = "topics"),
                    key = {"order.#", "user.*"}
            )
    })
    public void receive2(String message) {
        System.out.println("message2 =" + message);
    }
}
