package com.wt.springboot.rabbitmq;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2019-01-21 下午 4:37
 * PROJECT_NAME springBoot
 */
@Data
@Accessors(chain = true)
public class RabbitUser implements Serializable{

    private static final long serialVersionUID = -8394809458207204269L;

    private String name;
    private int age;
}
