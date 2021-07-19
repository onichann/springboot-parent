package com.wt.springboot.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Pat.Wu
 * @date 2021/7/16 17:58
 * 说明:
 */
public class Channel {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("d:\\channel.txt");
        FileChannel channel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("bufferChannel文件".getBytes());
        buffer.flip();
        channel.write(buffer);
        channel.close();
    }
}
