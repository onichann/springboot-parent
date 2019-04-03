package com.wt.springboot.data.jpa.QueryAndNamedQuery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_student_query")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
//NamedQuery 要放到查询的实体上，并且名称是实体的类名.方法名称
@NamedQuery(name = "Student_Query.findStudentsByClazzName2",query = "select s from  Student_Query s where s.clazz.name=?1")
public class Student_Query implements Serializable {

    private static final long serialVersionUID = 1103642835353934729L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name ;
    private String address ;
    private int age ;
    private char sex;
    // 学生与班级是多对一的关系，这里配置的是双向关联
    @ManyToOne(fetch=FetchType.EAGER,
            targetEntity=Clazz_Query.class
    )
    @JoinColumn(name="clazz",referencedColumnName="code")
    private Clazz_Query clazz ;

    public Student_Query(String name, String address, int age, char sex,
                         Clazz_Query clazz) {
        super();
        this.name = name;
        this.address = address;
        this.age = age;
        this.sex = sex;
        this.clazz = clazz;
    }
}
