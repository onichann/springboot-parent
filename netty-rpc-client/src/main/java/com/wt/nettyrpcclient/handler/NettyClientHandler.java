package com.wt.nettyrpcclient.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.DefaultPromise;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * @author wutong
 * @date 2022/4/24 14:43
 * 说明: 客户端业务处理类
 */
@Component
public class NettyClientHandler extends SimpleChannelInboundHandler<String> implements Callable<Object> {

    ChannelHandlerContext context = null;

    private String reqMsg;

    private String respMsg;

    public void setReqMsg(String reqMsg) {
        this.reqMsg = reqMsg;
    }

    /**
     * 通道读取就绪事件--读取服务端消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected synchronized void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        respMsg = msg;
        //唤醒等待线程
        notify();
    }

    /**
     * 通道连接就绪事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        context = ctx;
    }

    /**
     * 给服务端发送消息
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(reqMsg);
        DefaultPromise<Object> promise = new DefaultPromise<>(context.channel().eventLoop());
        
        //将线程处于等待状态
        wait();
        return respMsg;
    }
}
