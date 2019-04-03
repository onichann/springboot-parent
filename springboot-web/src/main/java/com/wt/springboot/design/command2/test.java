package com.wt.springboot.design.command2;


import com.wt.springboot.design.command2.command.Command;
import com.wt.springboot.design.command2.command.RedisQueryRequest;
import com.wt.springboot.design.command2.command.RedisUpdateRequest;
import com.wt.springboot.design.command2.invoker.Invoker;
import com.wt.springboot.design.command2.invoker.RedisInvoker;
import com.wt.springboot.design.command2.receiver.Receiver;
import com.wt.springboot.design.command2.receiver.RedisReceiver;

public class test {
    public static void main(String[] args) {
        Receiver<Product> receiver=new RedisReceiver<>();
        Command<Product> command= new RedisQueryRequest<>("test",receiver);
        Invoker<Product> invoker=new RedisInvoker<>(command);
        Product product=invoker.invoker(Product.class);
        System.out.println(product+","+product.getClass().getName());

        Receiver<Product> receiver2=new RedisReceiver<>();
        Command<Product> command2= new RedisUpdateRequest<>("test","ss",receiver2);
        Invoker<Product> invoker2=new RedisInvoker<>(command2);
        Integer product2=invoker2.invoker(Integer.class);
        System.out.println(product2+","+product2.getClass().getName());
    }
}
