package com.wt.springboot.dingding.mongodb;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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


    /**
     * 插入【一条】文档数据，如果文档信息已经【存在就抛出异常】
     *
     * @return 插入的文档信息
     */
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


    /**
     * 存储【一条】用户信息，如果文档信息已经【存在就执行更新】
     *
     * @return 存储的文档信息
     */
    public User save() {
        // 设置用户信息
        User user = new User()
                .setId("101")
                .setAge(222)
                .setSex("男")
                .setRemake("无")
                .setSalary(1400)
                .setName("zhangsan")
                .setBirthday(new Date())
                .setStatus(new Status().setHeight(180).setWeight(150));
        // 存储用户信息,如果文档信息已经存在就执行更新
        User newUser = mongoTemplate.save(user, COLLECTION_NAME);
        // 输出存储结果
        log.info("存储的用户信息为：{}", newUser);
        return newUser;
    }




    public Document test() {
        // 自定义命令
        String jsonCommand = "{\"buildInfo\":1}";
        // 将 JSON 字符串解析成 MongoDB 命令
        Bson bson = Document.parse(jsonCommand);
        // 执行自定义命令
        return mongoTemplate.getDb().runCommand(bson);
    }

    public List<User> findAll() {
        List<User> all = mongoTemplate.findAll(User.class, COLLECTION_NAME);
        for (User user : all) {
            log.info("用户信息：{}", user);
        }
        return all;
    }

    public User findById() {
        User user = mongoTemplate.findById("101", User.class, COLLECTION_NAME);
        log.info("用户信息：{}", user);
        return user;
    }

    public User findByCri() {
//        Criteria criteria=Criteria.byExample(Example.of())
    }

}
