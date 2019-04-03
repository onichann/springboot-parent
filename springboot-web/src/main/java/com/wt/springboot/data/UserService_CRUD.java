package com.wt.springboot.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService_CRUD {

    @Autowired
    private UserRepository_CRUD userRepository;

    @Transactional
    public User_CRUD save(User_CRUD user) {
        return userRepository.save(user);
    }

    @Transactional
    public void delete(int id){
        userRepository.deleteById(id);
    }

    public Iterable<User_CRUD> getAll(){
        return userRepository.findAll();
    }

    public User_CRUD getById(Integer id){
        Optional<User_CRUD> op=userRepository.findById(id);
        return op.orElse(null);
    }

    @Transactional
    public void update(User_CRUD user){
        // 直接调用持久化对象的set方法修改对象的数据
        user.setUsername("孙悟空");
        user.setLoginName("swk2");
        userRepository.save(user);
    }
}
