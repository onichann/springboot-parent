package com.wt.springboot.dingding;

import com.wt.springboot.dingding.test.Common;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDingdingApplicationTests {

//    @Autowired
//    private DocumentService documentService;

//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @SneakyThrows
//    @Test
//    public void test1() {
//        User user = documentService.insert();
//        System.out.println(objectMapper.writeValueAsString(user));
//    }
//
//
//    @SneakyThrows
//    @Test
//    public void test2() {
//        User user = documentService.save();
//        System.out.println(objectMapper.writeValueAsString(user));
//    }
//
//    @SneakyThrows
//    @Test
//    public void test3() {
//        Document document=documentService.test();
//        System.out.println(objectMapper.writeValueAsString(document));
//    }
//
//    @SneakyThrows
//    @Test
//    public void test4() {
//        List<User> all = documentService.findAll();
//        System.out.println(objectMapper.writeValueAsString(all));
//    }
//
//    @SneakyThrows
//    @Test
//    public void test5() {
//        User user = documentService.findById();
//        System.out.println(objectMapper.writeValueAsString(user));
//    }
//
//    @SneakyThrows
//    @Test
//    public void test6() {
//        List<User> all = documentService.findByCri();
//        System.out.println(objectMapper.writeValueAsString(all));
//    }

    @Autowired
    private Common common;

    @SneakyThrows
    @Test
    public void test7() {
         common.getToken();
    }


    @SneakyThrows
    @Test
    public void test8(){
        common.getBusiness();
    }
}
