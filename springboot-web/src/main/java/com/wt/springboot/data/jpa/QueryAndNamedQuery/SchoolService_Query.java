package com.wt.springboot.data.jpa.QueryAndNamedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchoolService_Query {
	
	// 注入数据访问层接口对象 
	@Autowired
	private StudentRepository_Query studentRepository;
	@Resource
	private ClazzRepository_Query clazzRepository;
	
	@Transactional
	public void saveClazzAll(List<Clazz_Query> clazzs) {
		clazzRepository.saveAll(clazzs);
	}
	@Transactional
	public void saveStudentAll(List<Student_Query> students) {
		studentRepository.saveAll(students);
	}
	
	public List<Map<String, Object>> getStusByClazzName(String clazzName) {
		// 使用"_" 和 @Query查询方式结果一致
//		List<Student_Query> students = studentRepository.findByClazz_name(clazzName);
		List<Student_Query> students = studentRepository.findStudentsByClazzName(clazzName);
		List<Map<String, Object>>  results = new ArrayList<>(); 
		// 遍历查询出的学生对象，提取姓名，年龄，性别信息
		for(Student_Query student:students){
			Map<String , Object> stu = new HashMap<>(); 
			stu.put("name", student.getName());
			stu.put("age", student.getAge());
			stu.put("sex", student.getSex());
			results.add(stu);
		}
		return results;
	}
	public List<Map<String, Object>> findNameAndSexByClazzName(String clazzName) {
		return studentRepository.findNameAndSexByClazzName(clazzName);
	}
	public List<String> findNameByClazzNameAndSex(
			String clazzName, char sex) {
		return studentRepository.findNameByClazzNameAndSex(clazzName, sex);
	}
	public String findClazzNameByStuName(String stuName) {
		return studentRepository.findClazzNameByStuName(stuName);
	}
	@Transactional
	public int deleteStuByStuName(String stuName) {
		return studentRepository.deleteStuByStuName(stuName);
	}

	public List<Map<String, Object>> getStusByClazzName2(String clazzName) {
		// 使用"_" 和 @Query查询方式结果一致
//		List<Student_Query> students = studentRepository.findByClazz_name(clazzName);
		List<Student_Query> students = studentRepository.findStudentsByClazzName2(clazzName);
		List<Map<String, Object>>  results = new ArrayList<>();
		// 遍历查询出的学生对象，提取姓名，年龄，性别信息
		for(Student_Query student:students){
			Map<String , Object> stu = new HashMap<>();
			stu.put("name", student.getName());
			stu.put("age", student.getAge());
			stu.put("sex", student.getSex());
			results.add(stu);
		}
		return results;
	}
}
