package com.wt.springboot.aesrsa;

import cn.hutool.crypto.SecureUtil;

/**
 * @author Pat.Wu
 * @date 2021/9/8 17:02
 * @description
 */
public class SecurityDemo {
    public static void main(String[] args) {
        String s = SecureUtil.sha1("111");
        String xxx = SecureUtil.md5("xxx");
        System.out.println(s);
        System.out.println(xxx);
    }
}
