package com.payinxl.common.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

public class DES3
{
    private static final String Algorithm = "DESede";

    public static byte[] decodeB64(String str64) throws Exception
    {
        BASE64Decoder decoder = new BASE64Decoder();
        byte b[] = decoder.decodeBuffer(str64);
        return (b);
    }

    public static String getBase64Str(byte[] strByte) throws Exception
    {
        BASE64Encoder encoder = new BASE64Encoder();
        String s = encoder.encode(strByte);
        return (s);
    }

    public static String getBase64Str(String str) throws Exception
    {
        BASE64Encoder encoder = new BASE64Encoder();
        String s = encoder.encode(str.getBytes());
        return (s);
    }

    public static String decodeBase64Str(String str64) throws Exception
    {
        BASE64Decoder decoder = new BASE64Decoder();
        byte b[] = decoder.decodeBuffer(str64);
        String s = new String(b);
        return (s);
    }

    // 定义 加密算法,可用 DES,DESede,Blowfish
    // keybyte为加密密钥，长度为24字节
    // src为被加密的数据缓冲区（源）

    public static byte[] encryptMode(byte[] keybyte, byte[] src)
    {

        try
        {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        }
        catch (java.security.NoSuchAlgorithmException e1)
        {
            e1.printStackTrace();
        }
        catch (javax.crypto.NoSuchPaddingException e2)
        {
            e2.printStackTrace();
        }
        catch (java.lang.Exception e3)
        {
            e3.printStackTrace();
        }

        return null;
    }

    public static String encryString(String src, String key) throws Exception
    {
        byte[] keyB = key.getBytes();
        byte[] srcB = src.getBytes();
        byte[] encodedB = encryptMode(keyB, srcB);
        return getBase64Str(encodedB);
    }

    public static byte[] md5(byte[] input) throws Exception
    {
        // or "SHA-1"
        MessageDigest alg = MessageDigest.getInstance("MD5");
        alg.update(input);
        byte[] digest = alg.digest();

        return digest;
    }

    // keybyte为加密密钥，长度为24字节
    // src为加密后的缓冲区
    public static byte[] decryptMode(byte[] keybyte, byte[] src)
    {

        try
        {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        }
        catch (java.security.NoSuchAlgorithmException e1)
        {
            e1.printStackTrace();
        }
        catch (javax.crypto.NoSuchPaddingException e2)
        {
            e2.printStackTrace();
        }
        catch (java.lang.Exception e3)
        {
            e3.printStackTrace();
        }

        return null;
    }

    // 转换成十六进制字符串
    public static String byte2hex(byte[] b)
    {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++)
        {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
            {
                hs = hs + "0" + stmp;
            }
            else
            {
                hs = hs + stmp;
            }
            if (n < b.length - 1)
            {
                hs = hs + ":";
            }
        }

        return hs.toUpperCase();
    }

    public static void main(String[] args) throws Exception
    {
        // 处理Key
        String key = "fc357037a9fabb2295871363";
        byte[] keyB = key.getBytes();

        // DES加密过程
        String src = "[SID:'222222',MID:'333333',KEY:'22222222',DATA:[....]]";
        long begintime= System.currentTimeMillis();
        byte[] srcB = src.getBytes();
        byte[] encodedB = encryptMode(keyB, srcB);
        String encoded = getBase64Str(encodedB);
        System.out.println("DES3密文=" + encoded);

        // DES解密过程
        encodedB = decodeB64(encoded);
        srcB = decryptMode(keyB, encodedB);
        src = new String(srcB);
        System.out.println("DES3明文=" + src);
    }
}
