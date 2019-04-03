package com.wt.springboot.rabbitmq;

/**
 * @author Administrator
 * @date 2019-01-21 下午 4:34
 * PROJECT_NAME springBoot
 */
public interface RabbitMQService {

    void sendMsg(String msg);

    void sendUser(RabbitUser user);
}
