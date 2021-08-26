package com.wt.springboot.dingding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wt.springboot.dingding.mongodb.DocumentService;
import com.wt.springboot.dingding.mongodb.User;
import lombok.SneakyThrows;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDingdingApplicationTests {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    public void test1() {
        User user = documentService.insert();
        System.out.println(objectMapper.writeValueAsString(user));
    }


    @SneakyThrows
    @Test
    public void test2() {
        User user = documentService.save();
        System.out.println(objectMapper.writeValueAsString(user));
    }

    @SneakyThrows
    @Test
    public void test3() {
        Document document=documentService.test();
        System.out.println(objectMapper.writeValueAsString(document));
    }

}
