package com.wt.nettyrpcapi.api;

import com.wt.nettyrpcapi.pojo.User;

/**
 * @author wutong
 * @date 2022/4/21 12:37
 * 说明:
 */
public interface IUserService {

    User getUserById(int id);
}
