package com.wt.springboot.dingding.test;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Pat.Wu
 * @date 2021/9/7 14:21
 * @description
 */
@Service
public class Common {


    @Autowired
    private ObjectMapper objectMapper;

    public void getToken() throws Exception{
        String appId = "25c0665a-0ad4-11ec-9427-0a80ff2603de";
        String appSecret = "F286#ZPygafMC47e#^4^rPB7!^";
//        String url = "http://hrpdev1.hrpackage.com/bf-portal/des/oauth/token";
        String url = "http://127.0.0.1:8080/bf-portal/des/oauth/token";
//        String url = "http://ujrzri.natappfree.cc/bf-portal/des/oauth/token";
        Map<String, Object> params = new HashMap<>();
        params.put("appId", appId);
        params.put("appSecret", appSecret);
        String post = HttpUtil.post(url, objectMapper.writeValueAsString(params));
        System.out.println(post);
    }

    public void getBusiness() throws Exception{
        String token = "$1$QkBjJ/i6$QLBXHZKuYwIEDj9cN4wHx/";
        //RSA私钥
        String primaryKeyString = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALWoT5dcKH0kS5T/iIBnKIsAsNY8HPo+a7G8nGkOyKmIVH4gxXjlyTDyfHwe72ZJsoAe89bIDEER4OEQAmGvI0bz05OcdbYulJXKp5kkkYSSFEl80mJTmmamkQ7OvZElQWmaJ2LSavoVev8UxnT3akGbhoLHArPRcrd58jifI1qrAgMBAAECgYBu+oKVb+H0ggYC4xIbj+o+8Y8XcuYpI5VbomTT8go5OGaWH6NEtu1xD9NpaOTM0t1QVJiXcOO7pRaDqOorj3m2yYpPLCGGUDwK0bzqg4iEffc9KsvcFyLe9WmuGORMju32XB71Pv+4PgjGqddNkcIgOrcUw1gJ5R+rPLw0wdKGAQJBAN3Pub08Ox10WyZxA8h1EsnCOebnYdzG4QkswKJFbB4VTJ1ZtR6DibdukPF0M80/Ehq++N2ec7vAYoJrbi3/kysCQQDRqC7lRMuOH/dxVQEOZIrOJAe4inkzYIHkSJjjtDMtIti5tb5kI9qFWhYdgMfQYxtenV4i2uagVR5zH+pv3ZaBAkEAhbjzy0gCg5FgWl6L30/lUclMSw53izhC9tsKD0o3EjPZCovIi2rVncaEj2x0xmodqg4zzrf2IdysBuBpfkW0HQJBAJCr1Ehik2/sQQwUUxlCacHbfPRroTzBoIANVGr0AshnnlNvxQRG9VhFlLMhFUCH0vAT+Uxl7vS+J7fbUyOELYECQECPeJVD6ex+4dk7eJ1LRslYY6ioNNxslq33xyEZVeGJgflLeRpm5yk8eFzw8/Ll9kUfzVePQ0a3kOQgJzTuIYk=";
//AES密钥
        String aesKey = "zpn62pci5o9o8bif";
//当前时间戳
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//本次请求ID
        String requestId = UUID.randomUUID().toString();
//业务参数请求body
        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("startDate", "2021-09-09");
//将业务参数转换成json文本
        String requestBodyJSON = objectMapper.writeValueAsString(requestBody);

//请求参数
        Map<String, Object> requestParamsMap = new LinkedHashMap<>();
        requestParamsMap.put("timestamp", timestamp);
        requestParamsMap.put("requestId", requestId);
//body进行AES加密
        requestParamsMap.put("requestBody", aesEncrypt(requestBodyJSON, aesKey));
//进行RSA签名
        requestParamsMap.put("signature", sha1RSASign(requestId + timestamp + requestBodyJSON, primaryKeyString));
//        String result = HttpUtil.post("http://hrpdev1.hrpackage.com/bf-portal/des/60110001",requestParamsMap);
//        HttpResponse response = HttpUtil.createPost("http://127.0.0.1:8080/bf-portal/des/60110001").header("accessToken", token).header("Content-Type", "application/json").body(objectMapper.writeValueAsString(requestParamsMap)).execute();
        HttpResponse response = HttpUtil.createPost("http://127.0.0.1:8080/bf-portal/des/60110002").header("accessToken", token).header("Content-Type", "application/json").body(objectMapper.writeValueAsString(requestParamsMap)).execute();
        System.out.println(response.body());
    }


    /**
     * 私钥加签 JavaBase64类名是jdk1.8的Base64类
     */
    public static String sha1RSASign(String content, String privateKeyString) throws Exception {
        byte[] keyByte = Base64.getDecoder().decode(privateKeyString);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyByte);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory
                .generatePrivate(privateKeySpec);
        Signature dsa = Signature.getInstance("SHA1withRSA");
        dsa.initSign(privateKey);
        dsa.update(content.getBytes("UTF-8"));
        byte[] sig = dsa.sign();

        String rtnValue = new String(Base64.getEncoder().encode(sig));

        return rtnValue;
    }

    /**
     * 公钥验证 JavaBase64类名是jdk1.8的Base64类
     */
    public static boolean sha1RSAValidateSign(String origin, String secret, String publicKeyString) {
        try {
            byte[] keyByte = Base64.getDecoder().decode(publicKeyString);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyByte);

            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

            Signature dsa = Signature.getInstance("SHA1withRSA");
            dsa.initVerify(publicKey);
            dsa.update(origin.getBytes("UTF-8"));
            return dsa.verify(Base64.getMimeDecoder().decode(secret.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean initialized = false;

    private static byte[] IV = new byte[]{(byte) 0, (byte) 1, (byte) 2, (byte) 0, (byte) 1, (byte) 2, (byte) 0, (byte) 1, (byte) 2, (byte) 0, (byte) 1, (byte) 2, (byte) 0, (byte) 1, (byte) 2, (byte) 0};

    public static void initialize() {
        if (initialized) return;
        Security.addProvider(new BouncyCastleProvider());
        System.out.println(Security.getProviders());
        initialized = true;
    }

    /**
     * AES 加密操作
     */
    public static String aesEncrypt(String content, String password) {
        initialize();
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding","BC");// 创建密码器

            byte[] byteContent = content.getBytes("utf-8");
            GCMParameterSpec ivSpec = new GCMParameterSpec(128, IV);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password), ivSpec);// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密

            return new String(Base64.getEncoder().encode(result));//通过Base64转码返回
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES 解密操作
     */
    public static String aesDecrypt(String content, String password) {
        initialize();
        try {
            //实例化
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding","BC");
            GCMParameterSpec ivSpec = new GCMParameterSpec(128, IV);
            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password), ivSpec);
            //执行操作
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(result, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) throws NoSuchAlgorithmException {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;

        kg = KeyGenerator.getInstance("AES");

        //AES 要求密钥长度为 128

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
        kg.init(128, random);

        //生成一个密钥
        SecretKey secretKey = kg.generateKey();

        return new SecretKeySpec(secretKey.getEncoded(), "AES");// 转换为AES专用密钥
    }

}
