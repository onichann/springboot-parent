package com.wt.springboot.mybatis.mapper;


import com.wt.springboot.pojo.FKUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@Mapper
public interface UserDao {
	
	@Insert("insert into tb_user(login_name ,username ,password) "
				+ "values (#{loginName},#{username},#{password})")
	public int insertUser(FKUser user);
	
	/**
	 * 插入数据获取主键
	 */
	@Insert("insert into tb_user_m(login_name ,username ,password) "
			+ "values (#{loginName},#{username},#{password})")
	@Options(useGeneratedKeys=true,keyProperty="id",keyColumn="id")
	public void insertGetKey(FKUser user);
	
	
	@Select("select * from tb_user_m where username = #{username}")
	// 引用id="userResult"的@Results
	@ResultMap("userResult")
	public FKUser selectByUsername(@Param("username") String username);
	
	@Select("select * from tb_user_m")
	// @Results用于映射对象属性和数据库列，常用于对象属性和数据库列不同名情况
	@Results(id="userResult",value={
			@Result(id=true,column="id",property="id"),
			@Result(column="login_name",property="loginName"),
			@Result(column="password",property="password"),
			@Result(column="username",property="username")
		})
	public List<FKUser> findAll();
	
	
	@Delete("delete from tb_user_m where id=#{id}")
	public void delete(final Integer id);
	
	
	@Select("select * from tb_user_m where id=#{id}")
	// 引用id="userResult"的@Results
	@ResultMap("userResult")
	public FKUser findUserById(int id);
	
	@Update("update tb_user_m set username=#{username}, login_name=#{loginName} where id=#{id}")
	public void update(final FKUser user);
}
