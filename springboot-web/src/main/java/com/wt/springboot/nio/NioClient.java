package com.wt.springboot.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author Pat.Wu
 * @date 2021/8/6 11:51
 * 说明:
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel=SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
//        socketChannel.bind(new InetSocketAddress("127.0.0.1", 9999));
        socketChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
//            buffer.clear();
            String str = scanner.nextLine();
            buffer.put(str.getBytes());
            socketChannel.write(buffer);
            buffer.clear();
        }
        socketChannel.close();
    }
}
