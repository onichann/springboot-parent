package com.wt.springboot.common;

import java.util.UUID;

public class IDUtils {

    /**
     * 随机生成32位
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
