package com.wt.springboot.design.command;


import com.wt.springboot.design.command.command.Command;
import com.wt.springboot.design.command.command.ConcreteCommand;
import com.wt.springboot.design.command.invoker.Invoker;
import com.wt.springboot.design.command.receiver.Receiver;

public class MyTest {
    public static void main(String[] args) {
        Receiver receiver=new Receiver();
        Command command=new ConcreteCommand(receiver);
        Invoker invoker=new Invoker(command);
        String s=invoker.queryForObject("test",String.class);
        System.out.println(s);
        Product product=invoker.queryForObject("test2",Product.class);
        System.out.println(product);

    }
}
