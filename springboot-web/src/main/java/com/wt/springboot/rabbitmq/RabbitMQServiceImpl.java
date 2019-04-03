package com.wt.springboot.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.wt.springboot.common.IDUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author Administrator
 * @date 2019-01-21 下午 4:39
 * PROJECT_NAME springBoot
 */

/**
 * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
 * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
 */
@Service
public class RabbitMQServiceImpl implements RabbitMQService,RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    private String msgRouting = "msg";

    private String userRouting = "user";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMsg(String msg) {
        System.out.println("发送消息："+msg);
        //设置回调
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        System.out.println("exchange:"+rabbitTemplate.getExchange());
        rabbitTemplate.convertAndSend(msgRouting,msg);

    }

    @Override
    public void sendUser(RabbitUser user) {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.convertAndSend(userRouting,user);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("correlationData:"+JSON.toJSONString(correlationData));
        if (ack) {
            System.out.println("消息成功消费");
        }else{
            System.out.println("消息消费失败:"+cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String correlationId = message.getMessageProperties().getCorrelationId();
        String format = MessageFormat.format("消息：{0} 发送失败, 应答码：{1} 原因：{2} 交换机: {3}  路由键: {4}", correlationId, replyCode, replyText, exchange, routingKey);
        System.out.println("returnCallback:"+format);
    }

    public void sendDirect(Object msg){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.convertAndSend("direct","direct_routing_key",msg,new CorrelationData(IDUtils.getUUID()));
    }

    public void sendFanout(Object msg) {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.convertAndSend("fanout","",msg,new CorrelationData(IDUtils.getUUID()));
    }
}
