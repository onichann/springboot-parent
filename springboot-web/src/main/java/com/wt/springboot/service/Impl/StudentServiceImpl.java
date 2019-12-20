package com.wt.springboot.service.Impl;


import com.wt.springboot.utils.IDUtils;
import com.wt.springboot.pojo.Student;
import com.wt.springboot.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("StudentService")
public class StudentServiceImpl implements StudentService {
    @Override
    public List<Student> findAllStudent() {
        List<Student> studentList=new ArrayList<>();
        Student student= new Student();
        student.setFeatid(IDUtils.getUUID());
        student.setAge(18);
        student.setBirthday(new Date());
        student.setCreatetime(new Date());
        student.setName("张三");
        student.setScore(12.21);
        studentList.add(student);
        return studentList;
    }

    @Override
    public Student findStuByFeatid(String stuNo) {
        return null;
    }

    @Override
    public int saveStudent(Student student) {
        return 0;
    }

    //只读事务避免过程中数据改变带来影响
    @Transactional(readOnly = true)
    @Override
    public int updateStudent(String stuNo, Student student) {
        return 0;
    }

    @Override
    public int deleteStudent(String stuNo) {
        return 0;
    }
}
