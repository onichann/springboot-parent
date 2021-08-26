package com.wt.springboot.dingding.mongodb;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Pat.Wu
 * @date 2021/8/26 16:42
 * @description
 */
@Data
@Accessors(chain = true)
public class Status {

    private Integer weight;
    private Integer height;

}
