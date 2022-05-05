package com.wt.nettyrpcserver.service;

import com.wt.nettyrpcapi.api.IUserService;
import com.wt.nettyrpcapi.pojo.User;
import com.wt.nettyrpcserver.anno.RpcService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wutong
 * @date 2022/4/22 15:08
 * 说明:
 */
@RpcService
@Service
public class UserServiceImpl implements IUserService {

    Map<Integer, User> userMap = new HashMap<>();

    @Override
    public User getUserById(int id) {
        if (userMap.size() == 0) {
            User user = new User();
            user.setId(1);
            user.setName("张三");
            User user1 = new User();
            user1.setId(2);
            user1.setName("李四");
            userMap.put(user.getId(), user);
            userMap.put(user1.getId(), user1);
        }
        return userMap.get(id);
    }
}
