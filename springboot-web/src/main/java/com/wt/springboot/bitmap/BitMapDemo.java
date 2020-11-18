package com.wt.springboot.bitmap;

import java.util.Arrays;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * @author wutong
 * @date 2020/11/2 13:44
 * introduction
 */
public class BitMapDemo {

    public static void main(String[] args) {
        AtomicReference<Byte> aByte = new AtomicReference<>(new Byte("0"));
        IntStream.range(2014,2019).forEach(i->{
            aByte.set(Integer.valueOf(aByte.get()|(((byte)1)<<(i-2014))).byteValue());
        });
        System.out.println("原始数据:2014,2015,2016,2017,2018");
        System.out.println("测试数据:2019,2018,2020,2021,2017,2016");
        System.out.println("测试结果---------");
        Arrays.asList(2019,2018,2020,2021,2017,2016,2022,2099).forEach(i->{
            if ((aByte.get() & (((byte) 1) << (i - 2014))) == 0) {
                System.out.println(i+"不存在");
            }
        });
        System.out.println(aByte.get());
        System.out.println(Integer.toBinaryString(aByte.get()));

        BitSet bitSet = new BitSet(5);
        bitSet.set(0);
        bitSet.set(1);
        bitSet.set(2);
        bitSet.set(3);
        bitSet.set(4);
        System.out.println(bitSet.get(4));
        System.out.println(bitSet.get(5));
        System.out.println(bitSet.toString());
        System.out.println(bitSet.size());
        byte i=1;
        System.out.println(i<<6);
        System.out.println(i<<8);
        System.out.println(2^6-1);
        long[] map=new long[5];
        System.out.println(map[1]);

        System.out.println(1L<<129);
    }

}
