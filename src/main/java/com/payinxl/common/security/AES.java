package com.payinxl.common.security;

import org.springframework.util.Assert;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES
{

    // 加密
    public static String encrypt(String orig, String key) throws Exception
    {
        byte[] raw = getKey(key);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // "算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(orig.getBytes());
        // 此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return new BASE64Encoder().encode(encrypted);
    }

    // 解密
    public static String decrypt(String enc, String key) throws Exception
    {
        byte[] raw = getKey(key);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        // 先用base64解密
        byte[] encrypted = new BASE64Decoder().decodeBuffer(enc);
        byte[] original = cipher.doFinal(encrypted);
        return new String(original);
    }

    public static final byte[] fill(byte[] bytes, byte[] key)
    {
        if (bytes == null)
        {
            return null;
        }
        else
        {
            int bytesLength = bytes.length;
            int factor = key.length;

            if (bytesLength % factor == 0)
            {
                return bytes;
            }
            else
            {
                int newBytesLength = (bytesLength / factor + 1) * factor;
                byte[] newBytes = new byte[newBytesLength];

                for (int i = 0; i < bytesLength; i++)
                {
                    newBytes[i] = bytes[i];
                }

                for (int k = bytesLength; k < newBytesLength; k++)
                {
                    newBytes[k] = 0;
                }

                return newBytes;
            }
        }
    }

    public static final byte[] trim(byte[] bytes)
    {
        if (bytes == null)
        {
            return null;
        }
        else
        {
            int bytesLength = bytes.length;

            int i = bytesLength - 1;

            for (; i >= 0; i--)
            {
                if (bytes[i] != 0)
                {
                    break;
                }
            }

            byte[] newBytes = new byte[i + 1];

            for (int k = 0; k <= i; k++)
            {
                newBytes[k] = bytes[k];
            }

            return newBytes;
        }
    }

    public static byte[] encryptByte(String orig, String key) throws Exception
    {
        Assert.notNull(key, "Key不能为空");
        Assert.isTrue(key.length() == 16, "Key长度必须为16位");
        while (orig.length() % 16 != 0)
        {
            char c = 0;
            orig += c;
        }
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding"); // 创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(orig.getBytes());
        return encrypted;
    }

    public static byte[] decryptByte(byte[] encbyte, String key) throws Exception
    {
        byte[] raw = getKey(key);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding"); // 创建密码器
        // SecureRandom sr = new SecureRandom();
        // Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] original = cipher.doFinal(encbyte);
        return original;
    }

    private static byte[] getKey(String key)
    {
        Assert.notNull(key, "Key不能为空");
        Assert.isTrue(key.length() == 16, "Key长度必须为16位");
        return key.getBytes();
    }

    // 解密
    public static String decrypt_C(String enc, String key, String encode) throws Exception
    {
        byte[] raw = getKey(key);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] encrypted = new BASE64Decoder().decodeBuffer(enc);
        byte[] original = cipher.doFinal(encrypted);
        original = trim(original);
        String originalString = new String(original, encode);
        return originalString;
    }

    // 加密
    public static String encrypt_C(String orig, String key, String encode) throws Exception
    {
        Assert.notNull(key, "Key不能为空");
        Assert.isTrue(key.length() == 16, "Key长度必须为16位");
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(fill(orig.getBytes(encode), raw));
        // 此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return new BASE64Encoder().encode(encrypted);
    }

    public static void main(String[] args) throws Exception
    {
        /*
         * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
         * 此处使用AES-128-CBC加密模式，key需要为16位。
         */
        String cKey = "1234567890123456";
//        // 需要加密的字串
//        String cSrc = "xxx.com";
//        System.out.println(cSrc);
//        // 加密
//        long lStart = System.currentTimeMillis();
//        byte[] enString = AES.encryptByte(cSrc, cKey);
//        System.out.println("加密后的字串是：" + MyStringUtil.byte2hex(enString));
//
//        long lUseTime = System.currentTimeMillis() - lStart;
//        System.out.println("加密耗时：" + lUseTime + "毫秒");
//        // 解密
//        lStart = System.currentTimeMillis();
//        // 5F08F2A60C21F333A620E50297B2BF18
//        byte[] DeString = AES.decryptByte(MyStringUtil.hex2byte("5F08F2A60C21F333A620E50297B2BF18"), cKey);
//        System.out.println("解密后的字串是：" + new String(DeString));
//        lUseTime = System.currentTimeMillis() - lStart;
//        System.out.println("解密耗时：" + lUseTime + "毫秒");
//        System.out.println(new String(decryptByte(encryptByte("[game]", cKey), cKey)));
//        System.out.println(encrypt("[game]", cKey));
//        System.out.println("--");
//        System.out.println(decrypt("TwiQLPHtQfImMzo2moQmYA==", cKey));
        String orig = "[SID:'222222',MID:'333333',KEY:'22222222',DATA:[....]]";
        System.out.println(encrypt(orig, cKey));
        System.out.println("---");
        System.out.println(decrypt(encrypt(orig, cKey), cKey));

    }
}
