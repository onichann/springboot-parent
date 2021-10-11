package com.wt.springboot.aesrsa;

import cn.hutool.crypto.SecureUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Pat.Wu
 * @date 2021/9/8 16:25
 * @description
 */
@Service
public class RSAService {

    @Getter
    private RSAKey rsaKey;

    private final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKgXTCPV4PdA1BdtrCO3nRKRSajGmdy2WpMHZhK5b0AGc/kZE6KBYCEmmEVIjhMlGoHw3DSj3OfNAWAyOYqG6geyYpWXTNP3iYxw6YUCT7z8Fkf2v+RtPtfXEo5NzK0hwIvrfgDqk0fU1ocSGsG3mLxTuSnP/YGLHtqBG9oM1s0wIDAQAB";
    private final String PRIVATEKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIqBdMI9Xg90DUF22sI7edEpFJqMaZ3LZakwdmErlvQAZz+RkTooFgISaYRUiOEyUagfDcNKPc580BYDI5iobqB7JilZdM0/eJjHDphQJPvPwWR/a/5G0+19cSjk3MrSHAi+t+AOqTR9TWhxIawbeYvFO5Kc/9gYse2oEb2gzWzTAgMBAAECgYA7gCWilC5mQRq4ttB/hIFug1Fgm1V7/jbsGe+hCtLMe6ffPS/AZhweQ76kpJQw+ibuLHkDHk+y9xeFfU+7cxAipvIufqcyoR6eGxkwJRCJvTQWWMPsUCbDXVAtaNLzDJBkP6I2NnrYjMDaJPyFmt1u/HpHlBQtPqD+xl4B1coZcQJBAOzrGgeOSb+4RiOJ8KUwlThIb+hWt+MT3SPKjOUyXY77azMPQfB8Sb+gAfsHPvyiur/HsnSYZ1fDQU080CZB5CUCQQCVqTveBiUmFZ7pgM50oLiX5G/c/IhZNgCjFrxcMPc5Kq7SXEaHf1M9VAJntlhSVOGNiviPTQp8+F8k53BYQ/+XAkAjmr1hr0+EAXSA+MTyfleeirxOpWGjPaP5MgJ0m1oWF9Cnbr6RHoySwMArco5Ttzzrf15hSVoWhWB19BKv/S0FAkAF2ADVb4n9zbRRy8zD8w6iJ7JSf63XReuKcrXSJ7tPiSIgZqf+XHNknqYlZJJYTKYt/7Iq7kgtjwkwCLVzTNxrAkEArgdUoWyh5vwKhblS5wboTHnWBWeRhzKSxP1YGwJVhpQNIEI6JD5AJfJ49obvzVkhJamd/3WcvQ9oo+WlbviP0g==";


    private RSAService() {
        rsaKey = generateKey();
    }


    public RSAKey generateKey() {
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        return new RSAKey(Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()), Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded()));
    }


    public String generatePrivateKeyBase64() {
        return rsaKey.getPrivateKey();
    }

    public String generatePublicKeyBase64() {
        return rsaKey.getPublicKey();
    }


    /**
     * 加签
     *
     * @param plain
     * @return
     * @throws Exception
     */
    private byte[] sign(String plain) throws Exception {
        //根据对应算法，获取签名对象实例
        Signature signature = Signature.getInstance("SHA256WithRSA");
        //获取私钥，加签用的是私钥，私钥一般是在配置文件里面读的，这里为了演示方便
        PrivateKey privateKey = getPriveteKey(PRIVATEKEY);
        //初始化签名对象
        signature.initSign(privateKey);
        //把原始报文更新到对象
        signature.update(plain.getBytes(StandardCharsets.UTF_8));
        //加签
        return signature.sign();
    }

    private boolean verifySign(String plain, byte[] signatureBytes) throws Exception {
        //根据对应算法，获取签名对象实例
        Signature signature = Signature.getInstance("SHA256WithRSA");
        //获取公钥
        PublicKey publicKey = getPublicKey(PUBLICKEY);
        //初始化签名对象
        signature.initVerify(publicKey);
        //把原始报文更新到签名对象
        signature.update(plain.getBytes(StandardCharsets.UTF_8));
        //进行验签
        return signature.verify(signatureBytes);
    }

    private PublicKey getPublicKey(String publicKeyStr) {
        PublicKey publicKey = null;
        X509EncodedKeySpec x509EncodedKeySpec;
        try {
            x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }


    private PrivateKey getPriveteKey(String privateKeyStr) {
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec;
        try {
            pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }


    private byte[] encrypt(byte[] plain) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,getPublicKey(PUBLICKEY));
            return cipher.doFinal(plain);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] decrypt(byte[] plain) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,getPriveteKey(PRIVATEKEY));
            return cipher.doFinal(plain);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public String encryptBase64(String plain) {
        return Base64.getEncoder().encodeToString(encrypt(plain.getBytes(StandardCharsets.UTF_8)));
    }

    public String decryptBase64(String plain) {
        return new String(decrypt(Base64.getDecoder().decode(plain)));
    }




    public static void main(String[] args) throws Exception{
//        KeyPair pair = SecureUtil.generateKeyPair("RSA");
//        PrivateKey aPrivate = pair.getPrivate();
//        PublicKey aPublic = pair.getPublic();
//        System.out.println(Base64.getEncoder().encodeToString(aPrivate.getEncoded()));
//        System.out.println(Base64.getEncoder().encodeToString(aPublic.getEncoded()));
//        System.out.println("---");
//        System.out.println(Base64.getEncoder().encodeToString(aPrivate.getEncoded()));
//        System.out.println(Base64.getEncoder().encodeToString(aPublic.getEncoded()));
//---------------------
//        RSAKey rsaKey = new RSAService().getRsaKey();
//        System.out.println(rsaKey.getPublicKey());
//        System.out.println("======");
//        System.out.println(rsaKey.getPrivateKey());
        RSAService rsaService = new RSAService();
        //原始报文
        String plain = "欢迎大家关注我的公众号，捡小男孩的田螺";
        //加签
        byte[] signatureByte = rsaService.sign(plain);
        System.out.println("原始报文是:" + plain);
        System.out.println("加签结果:");
        System.out.println(new BASE64Encoder().encode(signatureByte));
        //验签
        boolean verifyResult = rsaService.verifySign(plain, signatureByte);
        System.out.println("验签结果:" + verifyResult);
        //---------------------
//        SecretKey aes = SecureUtil.generateKey("AES",128);
//        System.out.println(HexUtil.encodeHexStr(aes.getEncoded()));
////---------------------
//        StringBuffer shortBuffer = new StringBuffer();
//        String uuid = UUID.randomUUID().toString().replace("-", "");
//         String[] chars62 = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
//
//        for(int i = 0; i < 16; ++i) {
//            String str = uuid.substring(i * 2, i * 2 + 2);
//            int x = Integer.parseInt(str, 16);
//            shortBuffer.append(chars62[x % 62]);
//        }
//
//        System.out.println( shortBuffer.toString());

        String encryptPlain = rsaService.encryptBase64(plain);
        System.out.println("加密后:" + encryptPlain);
        System.out.println("解密后:"+rsaService.decryptBase64(encryptPlain));
    }


}
