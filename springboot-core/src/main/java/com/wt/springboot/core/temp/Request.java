package com.wt.springboot.core.temp;

/**
 * @author wutong
 * @date 2022/6/27 11:09
 * 说明:
 */
public class Request {
    Level level;
    public Request(Level level){
        this.level = level;
    }

    public Level getLevel(){
        return level;
    }
}
