package com.wt.springboot.controller;


import com.wt.springboot.pojo.User_Mybatis;
import com.wt.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/userc")
public class UserController {
	
	// 注入UserService
	@Autowired
	private UserService userService;
	
	@RequestMapping("/insertUser")
	public String insertUser(User_Mybatis user){
		return "插入数据["+userService.insertUser(user)+"]条";
	}
	
	@RequestMapping("/insertGetKey")
	public User_Mybatis insertGetKey(User_Mybatis user) {
		userService.insertGetKey(user);
		return user ;
	}
	
	@RequestMapping("/selectByUsername")
	public User_Mybatis selectByUsername(String username){
		return userService.selectByUsername(username);
	}
	
	@RequestMapping("/findAll")
	public List<User_Mybatis> findAll(){
		return userService.findAll();
	}
	
	@RequestMapping("/update")
	public void update(User_Mybatis user) {
		userService.update(user);
	}
	
	@RequestMapping("/delete")
	public void delete(Integer id) {
		userService.delete(id);
	}
}
