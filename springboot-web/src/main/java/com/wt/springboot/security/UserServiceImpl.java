package com.wt.springboot.security;


import com.wt.springboot.mybatis.mapper.UserMapper;
import com.wt.springboot.pojo.FKRole;
import com.wt.springboot.pojo.FKUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FKUser user = userRepository.findByLoginName(username);
//        FKUser user = userMapper.findByLoginName(username);  //mybatis查询，正常使用普通的pojo
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<FKRole> roles = user.getRoles();
        List<GrantedAuthority> grantedAuthorities = roles.stream().flatMap(v -> Stream.of(v.getAuthority())).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new User(user.getUsername(),user.getPassword(),grantedAuthorities);
    }
}
