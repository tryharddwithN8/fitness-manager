package com.fitness.dto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Encrypt
 */
public class Encrypt {

    /*
     * Hash Password 
     */

    public static String hash(String in)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(in.getBytes());
            
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
}