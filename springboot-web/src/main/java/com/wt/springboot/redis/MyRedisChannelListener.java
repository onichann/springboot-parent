package com.wt.springboot.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.Charset;

public class MyRedisChannelListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println("bytes "+new String(bytes,Charset.defaultCharset())); //订阅的频道名称 news.*
        byte[] channel = message.getChannel();
        byte[] body = message.getBody();
        try {
            String content = new String(body, Charset.defaultCharset());
            String p=new String(channel,Charset.defaultCharset()); //实际的频道名称 news.test 等
            System.out.println("get "+content+" from "+p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
