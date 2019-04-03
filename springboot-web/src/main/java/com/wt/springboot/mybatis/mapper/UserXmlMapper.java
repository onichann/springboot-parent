package com.wt.springboot.mybatis.mapper;


import com.wt.springboot.pojo.User_Mybatis;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserXmlMapper {

    public List<User_Mybatis> queryUsers();


}
