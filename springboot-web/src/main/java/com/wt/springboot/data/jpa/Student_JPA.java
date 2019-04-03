package com.wt.springboot.data.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tb_student_jpa")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student_JPA implements Serializable{

    private static final long serialVersionUID = -7172159876949735652L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name ;
    private String address ;
    private int age ;
    private char sex;
}
