package com.d3tech.app.unit;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密工具类
 */
public class AESEncryptor
{
    public static final String VIPARA = "1114144464453111";
    public static final String bm = "UTF-8";

    /**
     * 加密
     * @param cleartext 原始数据
     * @return 加密后文本
     */
    //加密
    public static String encrypt(String cleartext)
    {
        try
        {
            return encrypt("CEmGySxZCxa5xZoW", cleartext);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 本地加密
     * @param cleartext 原始数据
     * @return 加密后文本
     */
      //本地加密
    public static String encryptLocal(String cleartext)
    {
        try
        {
            return encrypt("CEmGySxZCxa5xZoW",cleartext);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 本地解密
     * @param encrypted 密文
     * @return 原始数据
     */
    //本地解密
    public static String decryptLocal(String encrypted)
    {
        try
        {
            return decrypt("CEmGySxZCxa5xZoW",encrypted);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     * @param dataPassword 密钥
     * @param cleartext 原始数据
     * @return 加密后内容
     * @throws Exception
     */
    private static String encrypt(String dataPassword, String cleartext)
            throws Exception
    {
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(cleartext.getBytes(bm));

        return Base64.encode(encryptedData);
    }

    /**
     * 解密
     * @param dataPassword 密钥
     * @param encrypted 密文
     * @return 原始数据
     * @throws Exception
     */
    private static String decrypt(String dataPassword, String encrypted)
            throws Exception
    {
        byte[] byteMi = Base64.decode(encrypted);
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);

        return new String(decryptedData, bm);
    }
}

