package com.wt.springboot.data.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService_JPA {

    @Autowired
    private StudentRepository_JPA repository;

    @Transactional
    public void saveAll(List<Student_JPA> students) {
        repository.saveAll(students);
    }

    public Student_JPA getStuByName(String name) {
        return repository.findByName(name);
    }

    public List<Student_JPA> getStusByNameAndAddress(String name, String address) {
        return repository.findByNameAndAddress(name,address);
    }

    public List<Student_JPA> getStusByNameLike(String name) {
        return repository.findByNameLike("%"+name+"%");
    }
}
