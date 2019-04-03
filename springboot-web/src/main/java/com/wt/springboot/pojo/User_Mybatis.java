package com.wt.springboot.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User_Mybatis implements Serializable{

	private static final long serialVersionUID = 109462185541152067L;
	private int id ;
	private String loginName ;
	private String username ;
	private String password;
	
}
