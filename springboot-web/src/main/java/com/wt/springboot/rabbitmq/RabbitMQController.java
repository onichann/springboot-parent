package com.wt.springboot.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2019-01-21 下午 5:28
 * PROJECT_NAME springBoot
 */
@RestController
public class RabbitMQController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @RequestMapping("/msg")
    public Object test(){
        rabbitMQService.sendMsg("this is a message!");
        return "200";
    }

    @RequestMapping("/user")
    public Object test2(){
        rabbitMQService.sendUser(new RabbitUser().setAge(1).setName("小刚"));
        return "200";
    }

    @RequestMapping("/direct")
    public ResponseEntity sendDirect() {
        ((RabbitMQServiceImpl)rabbitMQService).sendDirect("directMsg");
        String msg="20202";
        return ResponseEntity.status(HttpStatus.OK).contentLength(msg.getBytes().length+2).contentType(MediaType.APPLICATION_JSON_UTF8).body(msg);
    }

    @RequestMapping("/fanout")
    public ResponseEntity sendFanout() {
        ((RabbitMQServiceImpl)rabbitMQService).sendFanout("fanoutMsg");
        return ResponseEntity.status(HttpStatus.OK).contentLength("200".length()).contentType(MediaType.APPLICATION_JSON_UTF8).body("200");
    }
}
