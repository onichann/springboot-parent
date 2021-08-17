package com.wt.springboot.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author Pat.Wu
 * @date 2021/8/13 15:39
 * 说明:
 */
public class NioChatClient {

    private Selector selector;
    private SocketChannel socketChannel;
    private final int PORT = 9999;
    private final String IP = "127.0.0.1";
    private String username;

    private NioChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(IP, PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            username=socketChannel.getLocalAddress().toString();
            System.out.println(username+"--is OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendInfo(String message) {
        message=username+"说:"+message;
        try {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readInfo() {
        try {
            if (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.read(buffer);
                        buffer.flip();
                        System.out.println(new String(buffer.array(),0,buffer.remaining()));
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NioChatClient nioChatClient = new NioChatClient();
        new Thread(()->{
            while (true) {
                nioChatClient.readInfo();
            }
        }).start();
        //发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            nioChatClient.sendInfo(s);
        }
    }
}
