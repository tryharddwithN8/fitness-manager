package com.fitness.dto;

import javax.crypto.Cipher;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.io.OutputStream;

/**
 * Encrypt 
 * RSA Encryption
 */
public class EncryptRSA {

    private static final String IP_TARGET = "https://apt3233.id.vn/api/login/auth";

    // Public key 
    private static final String PUBLIC_KEY = 
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0cKxkEMEisuxeGnmY3j+" +
        "W4377e3XQWvUZUKgF6HsJqc/pT/3FKOBwmCx6tZ+sh5nhNtL8sAkw9VyD9f+pyJq" +
        "NakNc2j44ZPZVkmtF1/2Bg3iBI8a3n5EzlzTvCleEEyMPBNuicBvW1gtrXsqhRv3" +
        "t3TPybtMTpZkQ9cLvwu+nAIO9RD5ZZp6EBAFihaaPlFnHB/jzxnNSQd5PBtiTeDy" +
        "XQoq8HdScWn9apmrAD6dT8VDlO54YlaHmRYKK+A+qapsFkHE1qt1ZwDMraiGyhh8" +
        "eu38aBu3O54gy+yPlAoHDIhioGms6ixBU66Dg6yYODUm/hk8OvAtd2fHlO6vTOe5" +
        "VQIDAQAB";

    /**
     * Encrypt data using RSA
     *
     * @param data The plain text data to be encrypted
     * @return The encrypted data in Base64 format
     * @throws Exception if any encryption error occurs
     */
    public static String encryptAESKey(String aesKeyBase64) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(PUBLIC_KEY);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedAESKey = cipher.doFinal(Base64.getDecoder().decode(aesKeyBase64));

        return Base64.getEncoder().encodeToString(encryptedAESKey);
    }

    /**
     * Send encrypted data to the server
     *
     * @param encryptedData The encrypted data to be sent
     * @throws Exception if any HTTP or I/O error occurs
     */
    public static void sendData(String encryted_key, String encryptedData) throws Exception {
        URL url = new URL(IP_TARGET);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");    
    
        connection.setDoOutput(true);
        String jsonInputString = "{\"username\":\"" + encryted_key + "\", \"password\":\"" + encryptedData + "\"}";
        
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Data sent successfully.");
        } else {
            System.out.println("Failed to send data. Response code: " + responseCode);
        }
    }
    
    
}