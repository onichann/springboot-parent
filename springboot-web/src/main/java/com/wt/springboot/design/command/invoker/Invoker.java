package com.wt.springboot.design.command.invoker;

import com.wt.springboot.design.command.command.Command;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Invoker {

    @Getter
    @Setter
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public <T extends Serializable> T queryForObject(String key, Class<T> tClass){
        command.setKey(key);
        return command.execute(tClass);
    }

    public int updateObject(String key,Object data){
        command.setKey(key);
        command.setData(data);
        return command.execute(Integer.class);
    }
}
