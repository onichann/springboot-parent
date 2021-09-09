package com.wt.springboot.aesrsa;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.symmetric.AES;
import lombok.SneakyThrows;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author Pat.Wu
 * @date 2021/9/8 17:02
 * @description https://blog.csdn.net/cc_weige/article/details/84902593
 * https://www.cnblogs.com/wuer888/p/7890711.html
 * https://jishuin.proginn.com/p/763bfbd29a1d
 */
public class SecurityDemo {
    @SneakyThrows
    public static void main(String[] args) {
//        testAES();
        SecurityDemo securityDemo = new SecurityDemo();
        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJKpVocxIorA6qugI16RnLUkLRIyoTL+BBEDLSphtn53nk5gkv/Kn6PUw6ZeGibZQfPf0VE4RVGo42tw1TbaMHoabhFhZ8jnZinUYMW89z0Ut8WTJ9MduyN+FL27Oqzx8+L/vl5atc+TWjH1YNvC2oia5ZLmrLCIFe1ZlGqtZ+jFAgMBAAECgYAKik0Drk5TNoYnC30/QKvIgO8UTbs0FPlp15aZYyhgcmxYKaym4YMTnSjqffzXKeoJgt5iPe8NbVvazuMj7A5rvBI9prgH/xBRGIt/CRpo0JBudQRCWrbcFMs2pnjmwAWKLOR9ftCPCQYNDd88ltfKIaYls6WljfLDjT4TSIYrgQJBANgOA5irXldSJoRPVVNK3EkiNdJlh86/YCJxXXiCx6BMADGB+oFnDY+YojmPk9ewk7HaEZeNtPtcRG9n1n5u7nUCQQCtxudb8I5EZw4HGE9xUBeVfXt0hJWARPyqDYCQI/2eYr0rk8SGeLnibvEHWgGw4KL6NIWrHurUx34VrSXslWcRAkAHMYBBJwN/GMcbhKCso0NuU+tC1AqPgaOrweaAyqnm1mDzRQaYJFw5ObW9AODFP6XLOB151EgATnQg2W40y3C5AkBaoDqUBhYLsjrslE8J5x0FhxVVJLfa1x91h+keQsbHTPMewMdi4Z7/aaAll1j+Z4hXOADlxw/su8UThOMcSKYhAkB3wyTNi0gsMNraLl0jPbil1aTPYoFWwANfAUH50UITORkoYGkYvsD2aTtMnpxmMXtQfh3gJRE/0sGoxyiq3fUo";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCRKbQOiPZo4G/rQv3A+m0XcCDvyZv/c2SkvLEKRXcYBui80QHSSs1JsGHDz57nUYPaw0CSuFbZVqHZpf76f3mSZ0qJgoR9O6UmVDKACoKw8WuxyZHFdtyPfbSFwxac55SHjgvya9CxuDYPGcUlnyYSGtrBvI6YGGBQqbhGnD0FjwIDAQAB";
        String sign = sign("123", privateKey, "UTF-8");
        System.out.println(sign);
        System.out.println("---------------");
        String s = SecureUtil.sha1("123");
        String s1 = SecureUtil.rsa(privateKey, null).encryptBase64(s, StandardCharsets.UTF_8, KeyType.PrivateKey);
        System.out.println(s1);
        System.out.println("---------------");
        System.out.println(s.equals(s1));

    }



    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    public static String sign(String content, String privateKey, String encode) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(encode));
            byte[] signed = signature.sign();
            return new BASE64Encoder().encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private static  void  testAES() {
        AES aes = SecureUtil.aes("1111111111111111".getBytes());
        String xxxx = aes.encryptBase64("xxx".getBytes());
        System.out.println(xxxx);
        System.out.println(new String(aes.decryptFromBase64(xxxx)));
    }


}
