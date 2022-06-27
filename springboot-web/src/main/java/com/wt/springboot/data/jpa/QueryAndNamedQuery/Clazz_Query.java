package com.wt.springboot.data.jpa.QueryAndNamedQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tb_clazz_query")
@Data
@NoArgsConstructor
//@Builder
@AllArgsConstructor
public class Clazz_Query implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int code ;
	private String name ;
	// 班级与学生是一对多的关联
	@OneToMany(
			   fetch=FetchType.LAZY,
			   targetEntity=Student_Query.class,
			   mappedBy="clazz"
			)     
	private Set<Student_Query> students=new HashSet<>();

	public Clazz_Query(String name) {
		this.name = name;
	}
}
