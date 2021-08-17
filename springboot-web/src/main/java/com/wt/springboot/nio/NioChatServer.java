package com.wt.springboot.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author Pat.Wu
 * @date 2021/8/13 15:40
 * 说明:
 */
public class NioChatServer {

    private Selector selector;
    private ServerSocketChannel socketChannel;
    private final int PORT = 9999;

    public NioChatServer() {
        try {
            selector = Selector.open();
            socketChannel = ServerSocketChannel.open();
            socketChannel.bind(new InetSocketAddress(PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen() {
        System.out.println("监听线程: " + Thread.currentThread().getName());
        while (true) {
            try {
                if ((selector.select() > 0)){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()) {
                            SocketChannel channel = socketChannel.accept();
                            channel.configureBlocking(false);
                            channel.register(selector, SelectionKey.OP_READ);
                            System.out.println(channel.getRemoteAddress() + "上线");
                        }
                        if (selectionKey.isReadable()) {
                            readData(selectionKey);
                        }
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void readData(SelectionKey selectionKey) {
        //取到关联的channle
        SocketChannel channel = null;
        try {
            //得到channel
            channel = (SocketChannel) selectionKey.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count的值做处理
            if(count > 0) {
                buffer.flip();
                //把缓存区的数据转成字符串
                String msg = new String(buffer.array(),0,buffer.remaining());
                //输出该消息
                System.out.println("form 客户端: " + msg);
                //向其它的客户端转发消息(去掉自己), 专门写一个方法来处理
                sendInfoToOtherClients(msg, channel);
            }
        }catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了..");
                e.printStackTrace();
                //取消注册
                selectionKey.cancel();
                //关闭通道
                channel.close();
            }catch (IOException e2) {
                e2.printStackTrace();;
            }
        }
    }

    //转发消息给其它客户(通道)
    private void sendInfoToOtherClients(String msg, SocketChannel self ) throws  IOException{
        System.out.println("服务器转发消息中...");
        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());
        //遍历 所有注册到selector 上的 SocketChannel,并排除 self
        for(SelectionKey key: selector.keys()) {
            //通过 key  取出对应的 SocketChannel
            SelectableChannel targetChannel = key.channel();
            //排除自己
            if(targetChannel instanceof  SocketChannel && targetChannel != self) {
                //转型
                SocketChannel dest = (SocketChannel)targetChannel;
                //将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer 的数据写入 通道
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        //创建服务器对象
        NioChatServer groupChatServer = new NioChatServer();
        groupChatServer.listen();
    }

}
