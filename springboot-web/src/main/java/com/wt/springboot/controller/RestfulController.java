package com.wt.springboot.controller;


import com.wt.springboot.common.SpringContextUtil;
import com.wt.springboot.pojo.ReturnJson;
import com.wt.springboot.pojo.Student;
import com.wt.springboot.service.StudentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
public class RestfulController {

//    private static Logger logger = LogManager.getLogger(RestfulController.class);
    private static Log logger = LogFactory.getLog(RestfulController.class);

    @Autowired
    private StudentService studentService;

    @GetMapping("/system/student")
    public ReturnJson findAllStudents(){
        List<Student> studentList= studentService.findAllStudent();
        ReturnJson returnJson= SpringContextUtil.getCtx().getBean("returnJson",ReturnJson.class);
        returnJson.setCode(HttpStatus.OK.value());
        returnJson.setMessage("获取成功");
        returnJson.setSuccess(Boolean.TRUE);
        returnJson.setData(studentList);
        logger.info(returnJson);
        return returnJson;
    }

    @GetMapping("/system/student/{stuNo}")
    public ReturnJson findStuByFeatid(@PathVariable("stuNo") String stuNo){
        Student student= studentService.findStuByFeatid(stuNo);
        ReturnJson returnJson= SpringContextUtil.getCtx().getBean("returnJson",ReturnJson.class);
        returnJson.setCode(HttpStatus.OK.value());
        returnJson.setMessage("获取成功");
        returnJson.setSuccess(Boolean.TRUE);
        returnJson.setData(student);
        logger.info(returnJson);
        return returnJson;
    }

    @PostMapping("/system/student")
    public ReturnJson insertStudent(@RequestBody Student student){
        int saveStatus=studentService.saveStudent(student);
        ReturnJson returnJson= SpringContextUtil.getCtx().getBean("returnJson",ReturnJson.class);
        returnJson.setCode(HttpStatus.OK.value());
        returnJson.setMessage(saveStatus==1?"新增成功":"新增失败");
        returnJson.setSuccess(saveStatus==1?Boolean.TRUE:Boolean.FALSE);
        returnJson.setData(student);
        logger.info(returnJson);
        return returnJson;
    }

    //put 更改资源，客户端需提供完整的属性，patch 仅需要提供需要更改的属性，比如修改学生的角色
//    更新学生信息
    @PutMapping("/system/student/{stuNo}")
    public ReturnJson updateStudent(@PathVariable("stuNo") String featid,@RequestBody Student student){
        int updateStatus=studentService.updateStudent(featid,student);
        ReturnJson returnJson= SpringContextUtil.getCtx().getBean("returnJson",ReturnJson.class);
        returnJson.setCode(HttpStatus.OK.value());
        returnJson.setMessage(updateStatus==1?"修改成功":"修改失败");
        returnJson.setSuccess(updateStatus==1?Boolean.TRUE:Boolean.FALSE);
        returnJson.setData(student);
        logger.info(returnJson);
        return returnJson;
    }

    @DeleteMapping("/system/student/{stuNo}")
    public ReturnJson deleteStudent(@PathVariable("stuNo") String featid){
        int deleteStatus=studentService.deleteStudent(featid);
        ReturnJson returnJson= SpringContextUtil.getCtx().getBean("returnJson",ReturnJson.class);
        returnJson.setCode(HttpStatus.OK.value());
        returnJson.setMessage(deleteStatus==1?"删除成功":"删除失败");
        returnJson.setSuccess(deleteStatus==1?Boolean.TRUE:Boolean.FALSE);
        returnJson.setData(deleteStatus);
        logger.info(returnJson);
        return returnJson;
    }
}
