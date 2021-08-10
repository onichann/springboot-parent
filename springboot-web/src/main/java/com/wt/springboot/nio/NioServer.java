package com.wt.springboot.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author Pat.Wu
 * @date 2021/8/6 10:03
 * 说明:
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        System.out.println("----服务端启动---");
        ServerSocketChannel schannel = ServerSocketChannel.open();
        schannel.configureBlocking(false);
        schannel.bind(new InetSocketAddress(9999));
        Selector selector = Selector.open();
        schannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select()>0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = schannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    SocketChannel sChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = sChannel.read(buf)) > 0) {
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();
                    }
                }
                iterator.remove();
            }
        }
    }
}
