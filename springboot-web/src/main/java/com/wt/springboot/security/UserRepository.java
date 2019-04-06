package com.wt.springboot.security;


import com.wt.springboot.pojo.FKUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<FKUser,Long> {

    FKUser findByLoginName(String loginName);

}
