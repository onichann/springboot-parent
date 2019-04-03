package com.wt.springboot.design.command.command;

import com.alibaba.fastjson.JSON;
import com.wt.springboot.design.command.receiver.Receiver;


public class UpdateCommand extends Command {

    public UpdateCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public <R> R execute(Class<R> rClass) {
        int i=receiver.updateObject(key,data);
        return JSON.parseObject(JSON.toJSONString(i),rClass);
    }
}
