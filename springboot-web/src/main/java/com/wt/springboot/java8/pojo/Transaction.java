package com.wt.springboot.java8.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true) //链式创建
public class Transaction {
    private int id;
    private String type;
    private String value;
}
