package com.wt.springboot.service;


import com.wt.springboot.pojo.Student;

import java.util.List;

public interface StudentService {

    List<Student> findAllStudent();

    Student findStuByFeatid(String stuNo);

    int saveStudent(Student student);

    int updateStudent(String stuNo, Student student);

    int deleteStudent(String stuNo);

}
