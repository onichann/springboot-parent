package com.wt.springboot.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_role")
@Getter
@Setter
@ToString
@Accessors(chain = true) //链式创建
public class FKRole implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //指定主键生成策略，mysql默认为自增长
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name="authority")
    private String authority;


}
