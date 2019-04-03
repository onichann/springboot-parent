package com.wt.springboot.springtest.service;


import com.wt.springboot.springtest.bean.Student;
import com.wt.springboot.springtest.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class SchoolService {
	
	// 定义数据访问层接口对象 
	@Resource
	private StudentRepository studentRepository;
	
	@Transactional
	public void save(Student stu) {
		studentRepository.save(stu);
	}

	public Student selectByKey(Integer id) {
		Optional<Student> op = studentRepository.findById(id);
		return op.orElse(null);
	}

}
