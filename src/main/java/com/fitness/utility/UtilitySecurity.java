package com.fitness.utility;

import java.security.SecureRandom;
import java.util.stream.Collectors;

public class UtilitySecurity {

    private static final String letters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()-_=+[]{}|;:,.<>?/~`";
    private static final SecureRandom rand = new SecureRandom();
    

    public static String genKey(int length) {
        return rand.ints(length, 0, letters.length())
                     .mapToObj(i -> String.valueOf(letters.charAt(i)))
                     .collect(Collectors.joining());

    }
    
}
