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
        SocketChannel skChannel = null;
        try {
            skChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            skChannel.read(buffer);
            buffer.flip();
            System.out.println("服务端收到消息："+new String(buffer.array(),0,buffer.remaining()));
            sendToOtherClients(buffer, skChannel);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                System.out.println(skChannel.getRemoteAddress()+"下线");
                skChannel.close();//关闭通道
                selectionKey.cancel(); //取消注册
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void sendToOtherClients(ByteBuffer buffer, SocketChannel skChannel) throws IOException {
        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());
        for (SelectionKey selectionKey : selector.keys()) {
            SelectableChannel channel = selectionKey.channel();
            if(channel instanceof SocketChannel&& channel!=skChannel){
                ((SocketChannel) channel).write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        NioChatServer nioChatServer = new NioChatServer();
        nioChatServer.listen();
    }

}
