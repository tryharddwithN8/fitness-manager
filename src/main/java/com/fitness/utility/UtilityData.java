package com.fitness.utility;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * UtilityData
 */
public class UtilityData {

    
    private static final String BOT_TOKEN = "7808838383:AAF31p3SDBnFqPrnRlDwWQTcnIIcL9gQV8w";
    private static final String CHAT_ID = "-1002294422711";

    public static int sendFeedBack(String name, String phone, String donate, String rate, String content) {
        String message = "Feedback from: " + name + 
                         "\nPhone: " + phone +
                         "\nDonate: " + donate +
                         "\nRate: " + rate +
                         "\n--------- Content -----------" + 
                         "\nMessage: " + content;
        String apiUrl = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            String urlParams = "chat_id=" + CHAT_ID + "&text=" + message;

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = urlParams.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Feedback sent successfully.");
                return 1; // Success
            } else {
                System.out.println("Failed to send feedback. Response Code: " + responseCode);
                return 0; 
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Error
        }
    }
}
