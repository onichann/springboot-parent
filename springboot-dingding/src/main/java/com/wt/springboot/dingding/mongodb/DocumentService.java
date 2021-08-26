package com.wt.springboot.dingding.mongodb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Pat.Wu
 * @date 2021/8/26 16:47
 * @description
 */
@Service
@Slf4j
public class DocumentService {
    /** 设置集合名称 */
    private static final String COLLECTION_NAME = "users";

    @Autowired
    private MongoTemplate mongoTemplate;

    public User insert(){
        // 设置用户信息
        User user = new User()
                .setId("10")
                .setAge(22)
                .setSex("男")
                .setRemake("无")
                .setSalary(1500)
                .setName("zhangsan")
                .setBirthday(new Date())
                .setStatus(new Status().setHeight(180).setWeight(150));
        User newUser = mongoTemplate.insert(user, COLLECTION_NAME);
        // 输出存储结果
        log.info("存储的用户信息为：{}", newUser);
        return newUser;
    }
}
