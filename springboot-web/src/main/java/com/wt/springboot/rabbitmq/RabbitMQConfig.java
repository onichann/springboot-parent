package com.wt.springboot.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @date 2019-01-16 下午 5:25
 * PROJECT_NAME springBoot
 */
//@Configuration
public class RabbitMQConfig {

    private String msgQueueName = "msg";
    private String userQueueName = "user";

    @Bean
    public Queue msgQueue(){
        //boolean值代表是否持久化消息
        return new Queue(msgQueueName,true);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(userQueueName, true);
    }

    //-------------------------------------------direct-------------
    @Bean
    public Exchange directExchange(){
        return ExchangeBuilder.directExchange("direct").durable(Boolean.TRUE).build();
    }

    @Bean
    public Queue directQueue(){
        return QueueBuilder.durable("directQueue").build();
    }

    @Bean
    public Binding directBinding(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("direct_routing_key").noargs();
    }

    //----------------------------------------fanout------------
    @Bean
    public Exchange fanoutExchange(){
        return ExchangeBuilder.fanoutExchange("fanout").durable(Boolean.TRUE).build();
    }

    @Bean
    public Queue fanoutQueueA() {
        return QueueBuilder.durable("fanoutQueueA").build();
    }

    @Bean
    public Queue fanoutQueueB() {
        return QueueBuilder.durable("fanoutQueueB").build();
    }

    @Bean
    public Binding fanoutBindingA(){
        return BindingBuilder.bind(fanoutQueueA()).to(fanoutExchange()).with("").noargs();
    }

    @Bean
    public Binding fanoutBindingB(){
        return BindingBuilder.bind(fanoutQueueB()).to(fanoutExchange()).with("").noargs();
    }

}
