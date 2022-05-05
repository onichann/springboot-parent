package com.wt.nettyrpcclient.controller;

import com.wt.nettyrpcapi.api.IUserService;
import com.wt.nettyrpcapi.pojo.User;
import com.wt.nettyrpcclient.anno.RpcReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wutong
 * @date 2022/4/22 15:12
 * 说明:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RpcReference
    IUserService userService;

    @RequestMapping("/getUserById")
    public User getUserById(int id) {
        return userService.getUserById(id);
    }
}
