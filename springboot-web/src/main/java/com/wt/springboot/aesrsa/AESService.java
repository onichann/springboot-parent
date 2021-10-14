package com.wt.springboot.aesrsa;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author Pat.Wu
 * @date 2021/9/8 16:22
 * @description
 */
@Service
public class AESService {



    public static String generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    @SneakyThrows
    public static String encrypt(String plain, String key) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(Base64.getDecoder().decode(key),"AES"));
        return Base64.getEncoder().encodeToString(cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8)));
    }

    @SneakyThrows
    public static String decrypt(String plain, String key) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(Base64.getDecoder().decode(key),"AES"));
        return new String(cipher.doFinal(Base64.getDecoder().decode(plain)));
    }


    public static void main(String[] args) throws  Exception{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest("999".getBytes("UTF-8"));
        String encode = Hex.encodeHexString(hash);
        System.out.println(encode.equals(DigestUtil.sha256Hex("999","UTF-8")));

        KeyGenerator aes = KeyGenerator.getInstance("AES");
        aes.init(128);
        SecretKey secretKey = aes.generateKey();
        System.out.println(HexUtil.encodeHexStr(secretKey.getEncoded()));

        System.out.println(generateKey());

        String plain = "欢迎大家关注我的公众号，捡小男孩的田螺";
        String key = "LHn1VKKsJSXr/As916410A==";
        String encrypt = encrypt(plain, key);
        System.out.println(encrypt);
        System.out.println(decrypt(encrypt,key));
    }
}
