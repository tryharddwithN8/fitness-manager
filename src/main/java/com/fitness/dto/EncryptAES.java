package com.fitness.dto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptAES {
    private SecretKeySpec aesKey;


    public EncryptAES(String key) throws Exception {
        if (key.length() < 32) {
            throw new IllegalArgumentException("Khóa AES phải có độ dài 32 ký tự.");
        }
        byte[] keyBytes = key.getBytes("UTF-8");
        this.aesKey = new SecretKeySpec(keyBytes, "AES");
    }

    public String encryptData(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);

        byte[] encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptData(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

        return new String(decryptedBytes, "UTF-8");
    }
}
