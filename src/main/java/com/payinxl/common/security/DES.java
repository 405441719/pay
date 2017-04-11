package com.payinxl.common.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

public class DES
{
	private static byte[] desKey;
	static{
		desKey="1waxrf9eZxuvNihE".getBytes();
	}
    private DES() {}
    public static byte[] desEncrypt(byte[] plainText) throws Exception {
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = desKey;  
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        byte data[] = plainText;  
        byte encryptedData[] = cipher.doFinal(data);  
        return encryptedData;  
    }  
  
    public static byte[] desDecrypt(byte[] encryptText) throws Exception {
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = desKey;  
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key, sr);
        byte encryptedData[] = encryptText;  
        byte decryptedData[] = cipher.doFinal(encryptedData);  
        return decryptedData;  
    }  
  
    public static String encrypt(String input) {
        try {
			return base64Encode(desEncrypt(input.getBytes()));
		} catch (Exception e) {
			return "mP3G/6vVTftzxYBABMzF/4jZ5Mn4HXVkZRCc1pg8Q0tZ0EzJCOjaYA==";
		}  
    }  
  
    public static String decrypt(String input) throws Exception {
        byte[] result = base64Decode(input);  
        return new String(desDecrypt(result));
    }  
  
    public static String base64Encode(byte[] s) {
        if (s == null)  
            return null;  
        BASE64Encoder b = new sun.misc.BASE64Encoder();
        return b.encode(s);  
    }  
  
    public static byte[] base64Decode(String s) throws IOException {
        if (s == null)  
            return null;  
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(s);  
        return b;  
    }  
  
    public static void main(String[] args) throws Exception {
        String input = "123321";
        String miwen="Jj2WVW+kvpNT+w2aL+2XNpHkWeTVIHPxMhN1N/FLgj2VKoQlsb5B9AZKHW6hktfrcAQnQVuooe58PO0qdzSIGavn6HQjksuhblRIQy/aXD4KSv9lH/6RXthQJ8S18Kcj/TluUWey5Yz4jUHcqPb5wLrhyvxEbjBl1xDsUQat8f0NXtB9z1IusSJEhdVswbeA2I8E6HhhC3KWbEbslWknmw==";
        System.out.println("Encode:" + DES.encrypt(input));
        System.out.println(DES.decrypt("dsy+L2bCzMc="));
    }  
}
