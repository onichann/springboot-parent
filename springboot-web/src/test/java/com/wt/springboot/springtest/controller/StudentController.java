package com.wt.springboot.springtest.controller;


import com.wt.springboot.springtest.bean.Student;
import com.wt.springboot.springtest.service.SchoolService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
	@Resource
	private SchoolService shcoolService;
	
	@PostMapping(value="/save")
	public Map<String,Object> save(@RequestBody Student stu) {
		shcoolService.save(stu);
		Map<String,Object> params = new HashMap<>();
		params.put("code", "success");
		return params;
	}
	
	/**
     * 获取学生信息
     * @param id
     */
    @GetMapping(value="/get/{id}")
    @ResponseBody
    public Student qryStu(@PathVariable(value = "id") Integer id){
    	Student stu = shcoolService.selectByKey(id);
        return stu;
    }
	
}
