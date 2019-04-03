package com.wt.springboot.data.jpa.QueryAndNamedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student2")
public class StudentController_Query {
	
	// 注入ShcoolService
	@Autowired
	private SchoolService_Query schoolService;

	@RequestMapping("/save")
	public String save() {
		
		Clazz_Query clazz1 = new Clazz_Query("疯狂java开发1班");
		Clazz_Query clazz2 = new Clazz_Query("疯狂java开发2班");
		// 保存班级对象数据
		List<Clazz_Query> clazzs = new ArrayList<>();
		clazzs.add(clazz1);
		clazzs.add(clazz2);
		schoolService.saveClazzAll(clazzs);
		
		Student_Query swk = new Student_Query("孙悟空","广州",700,'男',clazz1);
		Student_Query zzj = new Student_Query("蜘蛛精","广州",700,'女',clazz1);
		Student_Query nmw = new Student_Query("牛魔王","广州",500,'男',clazz2);
		
		List<Student_Query> students = new ArrayList<>();
		students.add(swk);
		students.add(zzj);
		students.add(nmw);
		schoolService.saveStudentAll(students);
		return "保存学生对象成功";
	}
	/**
	 * 查询某个班级下所有的学生姓名，年龄，性别
	 * @param clazzName
	 * @return
	 */
	@RequestMapping("/getClazzStus")
	public List<Map<String, Object>> getClazzStus(String clazzName){
		return schoolService.getStusByClazzName(clazzName);
	} 
	/**
	 * 查询某个班级下所有的学生姓名，性别
	 * @param clazzName
	 * @return
	 */
	@RequestMapping("/findNameAndSexByClazzName")
	public List<Map<String, Object>> findNameAndSexByClazzName(String clazzName){
		return schoolService.findNameAndSexByClazzName(clazzName);
	} 
	/**
	 * ，查询某个班级下某种性别的所有学生的姓名
	 * @param clazzName
	 * @return
	 */
	@RequestMapping("/findNameByClazzNameAndSex")
	public List<String> findNameByClazzNameAndSex(String clazzName ,Character sex){
		return schoolService.findNameByClazzNameAndSex(clazzName ,sex);
	} 
	/**
	 * 查询某个学生属于哪个班级
	 * @param
	 * @return
	 */
	@RequestMapping("/findClazzNameByStuName")
	public String findClazzNameByStuName(String stuName){
		return schoolService.findClazzNameByStuName(stuName);
	} 
	/**
	 * 删除某个学生对象
	 * @param
	 * @return
	 */
	@RequestMapping("/deleteStuByStuName")
	public String deleteStuByStuName(String stuName){
		return "删除数据："+schoolService.deleteStuByStuName(stuName);
	}

	@RequestMapping("/getClazzStus2")
	public List<Map<String, Object>> getClazzStus2(String clazzName){
		return schoolService.getStusByClazzName2(clazzName);
	}

}
