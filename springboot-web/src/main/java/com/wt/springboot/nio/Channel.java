package com.wt.springboot.nio;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.FileInputStream;
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

    @SneakyThrows
    @Test
    public void read() {
        FileInputStream fs = new FileInputStream("d:\\channel.txt");
        FileChannel channel = fs.getChannel();
        int available = fs.available();
        ByteBuffer buffer = ByteBuffer.allocate(available);
        channel.read(buffer);
//        buffer.flip();
        String str = new String(buffer.array(), 0, available);
        System.out.println(str);
    }

    @SneakyThrows
    @Test
    public void copy() {
        FileInputStream fs = new FileInputStream("d:\\channel.txt");
        FileOutputStream fos=new FileOutputStream("d:\\copy_channel.txt");
        FileChannel readChannel = fs.getChannel();
        FileChannel writeChannel = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            buffer.clear();
            if (readChannel.read(buffer) == -1) {
                break;
            }
            buffer.flip();
            writeChannel.write(buffer);
        }
        readChannel.close();
        writeChannel.close();
        System.out.println("复制完成!");
    }
}
