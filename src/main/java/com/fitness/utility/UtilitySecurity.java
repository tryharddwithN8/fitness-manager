package com.fitness.utility;

import java.security.SecureRandom;
import java.util.stream.Collectors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UtilitySecurity {
    private static final String API_URL = "https://apt3233.id.vn/resetpasswd/";
    private static final String letters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()-_=+[]{}|;:,.<>?/~`";
    private static final SecureRandom rand = new SecureRandom();
    

    public static String genKey(int length) {
        return rand.ints(length, 0, letters.length())
                     .mapToObj(i -> String.valueOf(letters.charAt(i)))
                     .collect(Collectors.joining());

    }

    public static String getKeyFromServer(String email) throws Exception{
        String key = fetchKey(email);
        System.err.println(key);
        return key;
    }
    
    public static String fetchKey(String email) throws Exception {
        String query = "?email=" + email;
    
        URL url = new URL(API_URL + query);
        System.out.println("Full URL with query: " + url.toString());
    
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);  
    
        String jsonInputString = "{\"email\":\"" + email + "\"}";    
    
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString().trim();
            }
        } else {
            System.out.println("Failed to fetch key. Response code: " + responseCode);
            return null;
        }
    }
    
    

}
