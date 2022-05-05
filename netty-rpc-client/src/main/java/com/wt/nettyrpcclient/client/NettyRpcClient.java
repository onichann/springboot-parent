package com.wt.nettyrpcclient.client;

import com.wt.nettyrpcclient.handler.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author wutong
 * @date 2022/4/22 17:26
 * 说明:Netty客户端
 * 1.连接服务端
 * 2.关闭资源
 * 3.提供发送消息方法
 */
@Component
public class NettyRpcClient implements InitializingBean, DisposableBean {

    NioEventLoopGroup group = null;

    Channel channel = null;


    @Autowired
    private NettyClientHandler nettyClientHandler;

    ExecutorService service = Executors.newCachedThreadPool();

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            //1.创建线程组
            group = new NioEventLoopGroup();
            //2.创建客户端启动助手
            Bootstrap bootstrap = new Bootstrap();
            //3.设置参数
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //添加编解码器
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            //添加自定义处理类
                            ch.pipeline().addLast(nettyClientHandler);
                        }
                    });
            //4.连接服务
            channel = bootstrap.connect("127.0.0.1", 8899).sync().channel();
//            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            if (group != null) {
                group.shutdownGracefully();
            }
            if (channel != null) {
                channel.close();
            }
        }

    }

    @Override
    public void destroy() throws Exception {
        if (group != null) {
            group.shutdownGracefully();
        }
        if (channel != null) {
            channel.close();
        }
    }

    /**
     * 消息发送
     * @param msg
     * @return
     */
    public Object sendMessage(String msg) throws ExecutionException, InterruptedException {
        nettyClientHandler.setReqMsg(msg);
        Future<Object> future = service.submit(nettyClientHandler);
        return future.get();
    }
}
