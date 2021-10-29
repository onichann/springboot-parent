package com.wt.springboot.dingding;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pat.Wu
 * @date 2021/10/29 14:23
 * @description
 */
@RestController
@Slf4j
public class KafkaController {

    @Autowired private KafkaTemplate kafkaTemplate;

    @GetMapping("/add")
    public void add() {
        //language=JSON5
        String user = "{\"id\": 999,\"name\": \"测试\"}";
        ListenableFuture<SendResult<String,Object>> future = kafkaTemplate.send("addUserTopic", user);
        future.addCallback(result->{
            if (result != null) {
                log.info("⽣产者成功发送消息到topic:{},partition:{}的消息",result.getRecordMetadata().topic(),result.getRecordMetadata().partition());
            }
        },ex->{
            log.info("⽣产者发送消失败，原因：{}",ex.getMessage());
        });
    }
}
