package com.wt.springboot.service;


import com.wt.springboot.mybatis.mapper.UserDao;
import com.wt.springboot.pojo.User_Mybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.List;

@Service
public class UserService {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	// 注入UserRepository
	@Autowired
	private UserDao userRepository;
	
	public int insertUser(User_Mybatis user){
		return userRepository.insertUser(user);
	}
	
	public User_Mybatis selectByUsername(String username){
		return userRepository.selectByUsername(username);
	}
	
	public List<User_Mybatis> findAll(){
		return userRepository.findAll();
	}
	
	public void insertGetKey(User_Mybatis user) {
		// 数据插入成功以后，Mybatis框架会将插入成功的数据主键存入到user对象中去
		userRepository.insertGetKey(user);
		WebApplicationContext currentWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = currentWebApplicationContext.getServletContext();
	}
	
	public void update(User_Mybatis user) {
		userRepository.update(user);
	}
	
	public void delete(Integer id) {
		userRepository.delete(id);
	}
}