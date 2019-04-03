package com.wt.springboot.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Administrator
 * @date 2019-01-21 下午 5:19
 * PROJECT_NAME springBoot
 */
@Component
public class RabbitMQReceiver {

    @RabbitListener(queues = "msg")
    public void receiveMsg(String msg) {
        System.out.println("收到消息："+msg);
    }

//    @RabbitListener(queues = "user")
    public void receiveUser(RabbitUser rabbitUser) {
        System.out.println("收到用户："+rabbitUser);
    }

    @RabbitListener(queues = "directQueue")
    public void directReceiver(Message message,Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("directQueue收到消息:"+new String(message.getBody()));
    }

    @RabbitListener(queues = "fanoutQueueA")
    public void fanoutAReceiver(Message message,Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("fanoutQueueA收到消息:"+new String(message.getBody()));
    }

    @RabbitListener(queues = "fanoutQueueB")
    public void fanoutBReceiver(Message message,Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("fanoutQueueB收到消息:"+new String(message.getBody()));
    }
}
