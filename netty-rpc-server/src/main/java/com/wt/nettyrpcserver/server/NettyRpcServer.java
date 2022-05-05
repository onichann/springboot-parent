package com.wt.nettyrpcserver.server;

import com.wt.nettyrpcserver.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wutong
 * @date 2022/4/22 15:42
 * 说明:netty服务端  启动服务端监听端口
 */

@Component
public class NettyRpcServer implements DisposableBean {

    @Autowired
    private NettyServerHandler nettyServerHandler;

    EventLoopGroup bossGroup = null;
    EventLoopGroup workerGroup = null;

    public void start(String host, int port) {

        try {
            //1.创建bossGroup 和workerGroup
             bossGroup = new NioEventLoopGroup(1);
             workerGroup= new NioEventLoopGroup();
            //设置启动助手
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置启动参数
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //添加String的编解码器
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            //添加自定义处理器
                            ch.pipeline().addLast(nettyServerHandler);
                        }
                    });
            //绑定ip和端口号
            ChannelFuture channelFuture = bootstrap.bind(host, port).sync();
            System.out.println("============Netty服务端启动成功============");
            //监听通道的关闭状态
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }
            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
        }

    }

    @Override
    public void destroy() throws Exception {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}
