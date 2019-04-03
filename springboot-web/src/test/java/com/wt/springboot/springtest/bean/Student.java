package com.wt.springboot.springtest.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tb_student")
@Data
@Accessors(chain = true) //链式创建
public class Student implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name ;
	private String address ;
	private int age ; 
	private char sex;
}
