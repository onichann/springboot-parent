package com.wt.springboot.nio;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.*;
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
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
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

    @Test
    public void ScatterAndGather() throws IOException {
//        byte[] bytes = "环境：1".getBytes();
        RandomAccessFile raf1 =new RandomAccessFile("d:\\1.txt", "rw");
        ByteBuffer bf1 = ByteBuffer.allocate(10);
        ByteBuffer bf2 = ByteBuffer.allocate(1024);
        FileChannel channel = raf1.getChannel();
        channel.read(new ByteBuffer[]{bf1, bf2});
        bf1.flip();
        bf2.flip();
        System.out.println(new String(bf1.array(), 0, bf1.remaining()));
        System.out.println("-------------------");
        System.out.println(new String(bf2.array(), 0, bf2.remaining()));
        RandomAccessFile raf2 =new RandomAccessFile("d:\\2.txt", "rw");
        raf2.getChannel().write(new ByteBuffer[]{bf1, bf2});
    }

    @Test
    public void transferFile() throws IOException {
        FileInputStream fs = new FileInputStream("d:\\1.txt");
        FileOutputStream fos = new FileOutputStream("d:\\5.txt");
        FileOutputStream fos2 = new FileOutputStream("d:\\6.txt");
        FileChannel fsChannel = fs.getChannel();
        FileChannel fosChannel = fos.getChannel();
        fsChannel.transferTo(fsChannel.position(), fsChannel.size(), fosChannel);

        FileChannel fos2Channel = fos2.getChannel();
        fos2Channel.transferFrom(fsChannel, fsChannel.position(), fsChannel.size());

        fsChannel.close();
        fosChannel.close();
        fos2.close();
    }

}
