package com.wt.springboot.core.temp;

/**
 * @author wutong
 * @date 2022/6/27 11:09
 * 说明:
 */
public class Level {

    private int level = 0;
    public Level(int level){
        this.level = level;
    };

    public boolean above(Level level){
        if(this.level >= level.level){
            return true;
        }
        return false;
    }

}

