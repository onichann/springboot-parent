package com.wt.springboot.mybatis.mapper;


import com.wt.springboot.pojo.FKRole;
import com.wt.springboot.pojo.FKUser;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Mapper
@Repository
public interface UserMapper {

    /**
     * 通过loginName查询用户信息，同时关联查询权限
     * @param loginName
     * @return
     */
    @Select("select * from tb_user where login_name =#{loginName}")
    @Results({@Result(id = true,column = "id",property = "id"),
            @Result(column = "login_name",property = "loginName"),
            @Result(column = "password",property = "password"),
            @Result(column = "id",property = "roles",
                    many =@Many(select = "findRoleByUser",fetchType = FetchType.EAGER))
    })
    FKUser findByLoginName(String loginName);

    /**
     * 通过用户id查询权限
     * @param id
     * @return
     */
    @Select("select t0.* from tb_role t0,tb_user_role t1 where t0.id=t1.role_id and t1.user_id=#{id,jdbcType=BIGINT}")
    List<FKRole> findRoleByUser(Long id);
}
