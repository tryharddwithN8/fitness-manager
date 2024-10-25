package com.fitness.utility;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


/**
 * UtilityData
 */
public class UtilityData {

    
    private static final String BOT_TOKEN = "7808838383:AAF31p3SDBnFqPrnRlDwWQTcnIIcL9gQV8w";
    private static final String CHAT_ID = "-1002294422711";

    public static String getUserNameLap()
    {
        return System.getProperty("user.name");
    }

    public static int sendFeedBack(String name, String phone, String donate, String rate, String content) {
        String message = "Feedback from: " + getUserNameLap() +
                         "\nName: " + name +  
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

    public static int sendImageWithUsername(File imageFile, String email) {
        String apiUrl = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendPhoto";
        String username = getUserNameLap();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=---ContentBoundary");

            String boundary = "---ContentBoundary";
            String LINE_FEED = "\r\n";
            StringBuilder postDataBuilder = new StringBuilder();
            
            postDataBuilder.append("--").append(boundary).append(LINE_FEED);
            postDataBuilder.append("Content-Disposition: form-data; name=\"chat_id\"").append(LINE_FEED).append(LINE_FEED);
            postDataBuilder.append(CHAT_ID).append(LINE_FEED);

            postDataBuilder.append("--").append(boundary).append(LINE_FEED);
            postDataBuilder.append("Content-Disposition: form-data; name=\"caption\"").append(LINE_FEED).append(LINE_FEED);
            postDataBuilder.append("From: ").append(username).append(LINE_FEED);
            postDataBuilder.append("Email: ").append(email).append(LINE_FEED);
            postDataBuilder.append("Upload CV: User to Coach").append(LINE_FEED);

            postDataBuilder.append("--").append(boundary).append(LINE_FEED);
            postDataBuilder.append("Content-Disposition: form-data; name=\"photo\"; filename=\"").append(imageFile.getName()).append("\"").append(LINE_FEED);
            postDataBuilder.append("Content-Type: ").append("image/jpeg").append(LINE_FEED).append(LINE_FEED);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(postDataBuilder.toString().getBytes(StandardCharsets.UTF_8));
                
                try (FileInputStream inputStream = new FileInputStream(imageFile)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead); 
                    }
                    os.write(LINE_FEED.getBytes(StandardCharsets.UTF_8));
                    os.write(("--" + boundary + "--" + LINE_FEED).getBytes(StandardCharsets.UTF_8));
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return 1; // Success
                } else {
                    return 0; // Failure
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Error
        }
    }
}
