package com.wt.springboot.dao;


import com.wt.springboot.pojo.FKUser;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface UserRepository extends JpaRepository<FKUser,Long> {

    FKUser findByLoginName(String loginName);

}
