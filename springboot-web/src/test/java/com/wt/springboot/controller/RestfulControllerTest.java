package com.wt.springboot.controller;


import com.wt.springboot.SpringbootWebApplication;
import com.wt.springboot.common.IDUtils;
import com.wt.springboot.pojo.ReturnJson;
import com.wt.springboot.pojo.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= SpringbootWebApplication.class)
public class RestfulControllerTest {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Value("#{'http://127.0.0.1:8080/api/v1.0'}")
    private String uri;

    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
         restTemplate=restTemplateBuilder.build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findAllStudents() {
//        restTemplate.getForObject()
    }

    @Test
    public void findStuByFeatid() {
        String url=uri+"/system/student/{stuNo}";
        String stuNo="1";
        HttpEntity requestEntity=null;
        ParameterizedTypeReference<ReturnJson> typeReference=new ParameterizedTypeReference<ReturnJson>(){};
        ResponseEntity<ReturnJson> returnJsonResponseEntity= restTemplate.exchange(url, HttpMethod.GET,requestEntity,typeReference,stuNo);
        System.out.println(returnJsonResponseEntity.getBody().getData());
    }

    @Test
    public void insertStudent() {
//        Student student= SpringContextUtil.getCtx().getBean("student",Student.class);
        Student student= new Student();
        student.setFeatid(IDUtils.getUUID());
        student.setAge(18);
        student.setBirthday(getDate());
        student.setCreatetime(getDateTime());
        student.setName("张三");
        student.setScore(12.21);
        String res=restTemplate.postForObject(uri+"/system/student",student,String.class);
//        System.out.println(res);
        HttpEntity body=new HttpEntity(student);
        ResponseEntity<ReturnJson> returnJsonResponseEntity=restTemplate.exchange(uri+"/system/student", HttpMethod.POST,body, ReturnJson.class);
        System.out.println(returnJsonResponseEntity.getBody().getData());
    }

    private Date getDate(){
        LocalDate localDate = LocalDate.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    private Date getDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    @Test
    public void updateStudent() {
    }

    @Test
    public void deleteStudent() {
    }
}