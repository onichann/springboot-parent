package com.wt.springboot.controller;


import com.wt.springboot.common.SpringContextUtil;
import com.wt.springboot.pojo.ReturnJson;
import com.wt.springboot.pojo.Student;
import com.wt.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/testHttpClient")
public class HttpClientController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/get")
    public ReturnJson findAllStudents(@RequestParam String id){
        System.out.println("id="+id);
        List<Student> studentList= studentService.findAllStudent();
        ReturnJson returnJson= SpringContextUtil.getCtx().getBean("returnJson",ReturnJson.class);
        returnJson.setCode(HttpStatus.OK.value());
        returnJson.setMessage("获取成功");
        returnJson.setSuccess(Boolean.TRUE);
        returnJson.setData(studentList);
        System.out.println("studentList="+studentList);
        return returnJson;
    }
}
