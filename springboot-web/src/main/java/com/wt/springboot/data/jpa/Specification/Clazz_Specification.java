package com.wt.springboot.data.jpa.Specification;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name="tb_clazz__Specification")
public class Clazz_Specification implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int code ;
	private String name ;
	// 班级与学生是一对多的关联
	@OneToMany(
			   fetch=FetchType.EAGER,
			   targetEntity=Student_Specification.class,
			   mappedBy="clazz"
			)     
	private Set<Student_Specification> students = new HashSet<>();
	
	public Clazz_Specification() {
		
	}
	// 班级对象
	public Clazz_Specification(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Student_Specification> getStudents() {
		return students;
	}
	public void setStudents(Set<Student_Specification> students) {
		this.students = students;
	}
}
