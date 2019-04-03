package com.wt.springboot.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository_JPA extends JpaRepository<Student_JPA, Integer> {

    Student_JPA findByName(String name);

    /**
     * 通过名字和地址查询学生信息
     * 此方法相当于JPQL语句代码:select s from Student s where s.name = ?1 and s.address=?2
     * @param name
     * @param address
     * @return 包含Student对象的List集合
     */
    List<Student_JPA> findByNameAndAddress(String name, String address);

    /**
     * 通过学生姓名模糊查询学生信息
     * 此方法相当于JPQL语句代码:select s from Student s where s.name like ?1
     * @param name 参数
     * @return 包含Student对象的List集合
     */
    List<Student_JPA> findByNameLike(String name);
}
