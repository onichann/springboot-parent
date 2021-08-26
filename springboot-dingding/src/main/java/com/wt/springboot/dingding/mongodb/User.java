package com.wt.springboot.dingding.mongodb;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

/**
 * @author Pat.Wu
 * @date 2021/8/26 16:16
 * @description
 */
@Data
@Accessors(chain = true)
public class User {

    @MongoId
    private String id;
    private String name;
    private String sex;
    private Integer salary;
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;
    private String remake;
    private Status status;


}
