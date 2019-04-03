package com.wt.springboot.data.jpa.Specification;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="tb_student__Specification")
public class Student_Specification implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name ;
	private String address ;
	private int age ; 
	private char sex;
	// 学生与班级是多对一的关系，这里配置的是双向关联
	@ManyToOne(fetch=FetchType.EAGER,
			targetEntity=Clazz_Specification.class
			)
	@JoinColumn(name="clazzId",referencedColumnName="code")
	private Clazz_Specification clazz ;
	public Student_Specification() {

	}
	public Student_Specification(String name, String address, int age, char sex,
								 Clazz_Specification clazz) {
		super();
		this.name = name;
		this.address = address;
		this.age = age;
		this.sex = sex;
		this.clazz = clazz;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public Clazz_Specification getClazz() {
		return clazz;
	}
	public void setClazz(Clazz_Specification clazz) {
		this.clazz = clazz;
	}
}