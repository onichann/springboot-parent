package com.wt.springboot.data;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_user_crud")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User_CRUD implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //指定主键的生成策略,mysql默认的是自增长。
    private int id;

    private String username;

    private String loginName;

    private char sex;

    private int age;

}
