package com.wt.springboot.data.jpa;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController_JPA {
	
	// 注入StudentService
	@Resource
	private StudentService_JPA studentService;
	
	@RequestMapping("/save")
	public String save() {
		Student_JPA swk = new Student_JPA();
		swk.setAddress("广州");
		swk.setName("孙悟空");
		swk.setAge(700);
		swk.setSex('男');
		
		Student_JPA zzj = new Student_JPA();
		zzj.setAddress("广州");
		zzj.setName("蜘蛛精王");
		zzj.setAge(700);
		zzj.setSex('女');
		
		Student_JPA nmw = new Student_JPA();
		nmw.setAddress("广州");
		nmw.setName("牛魔王");
		nmw.setAge(500);
		nmw.setSex('男');
		
		List<Student_JPA> students = new ArrayList<>();
		students.add(swk);
		students.add(zzj);
		students.add(nmw);
		
		studentService.saveAll(students);
		return "保存学生对象成功";
	}
	
	@RequestMapping("/name")
	public Student_JPA getByName(String name) {
		return studentService.getStuByName(name);
	}
	
	@RequestMapping("/nameAndAddress")
	public List<Student_JPA> getByNameAndAddress(String name, String address) {
		return studentService.getStusByNameAndAddress(name, address);
	}
	
	@RequestMapping("/nameLike")
	public List<Student_JPA> getByNameLile(String name) {
		return studentService.getStusByNameLike(name);
	}

}
