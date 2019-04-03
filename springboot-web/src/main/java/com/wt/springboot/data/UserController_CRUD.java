package com.wt.springboot.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController_CRUD {

    // 注入UserService
    @Autowired
    private UserService_CRUD userService;

    @RequestMapping("/save")
    public String save() {
        User_CRUD user = User_CRUD.builder().loginName("dLei").username("徐磊").sex('男').age(3).build();
        user = userService.save(user);
        System.out.println("保存数据成功，返回的结果：" + user);
        return "保存数据成功！";
    }

    @RequestMapping("/update")
    public String update() {
        // 修改的对象必须是持久化对象，所以先从数据库查询出id为1的对象进行修改
        User_CRUD user = userService.getById(2);
        userService.update(user);
        return "修改数据成功!";
    }

    @RequestMapping("/delete")
    public String delete() {
        userService.delete(1);
        return "删除数据成功！";
    }

    @RequestMapping("/getAll")
    public Iterable<User_CRUD> getAll() {
        return userService.getAll();
    }
}
