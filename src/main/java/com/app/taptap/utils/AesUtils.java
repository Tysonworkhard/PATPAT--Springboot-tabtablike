package com.app.taptap.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesUtils {
    private String KEY; // 密钥
    private String IV; // 初始化向量

    public String encrypt(String data) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(KEY), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(IV));
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(KEY), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(IV));
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public void setIV(String IV) {
        this.IV = IV;
    }
}
