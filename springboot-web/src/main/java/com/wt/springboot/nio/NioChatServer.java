package com.wt.springboot.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
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
                            SocketChannel channel = (SocketChannel)selectionKey.channel();
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

    }


}
