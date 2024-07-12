package com.alexkim.powerliftingperformancetrackerv2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class PowerliftingCoach {
    private String apiKey;

    public PowerliftingCoach() {
        this.apiKey = "";
    }

    public String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String model = "gpt-3.5-turbo";
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + this.apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = buildRequestBody(prompt, model);
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = connection.getResponseCode();
            InputStream is = (code >= 200 && code < 300) ? connection.getInputStream() : connection.getErrorStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            br.close();

            if (code >= 200 && code < 300) {
                return extractMessageFromJSONResponse(response.toString());
            } else {
                throw new RuntimeException("Failed : HTTP error code : " + code + " Response: " + response.toString());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildRequestBody(String prompt, String model) {
        String customPrompt = "You will act as an AI powerlifting coach. You are to provide advice to lifters regarding the sport, their training, and etc. If the conversation deviates away from powerlifting or athletic or self improvement activities, tell the user that you cannot assist them with other things. Also make sure to introduce yourself as the AI Powerlifting Coach at the start of the conversation. But if the user just asks a question, get straight to the chase and just answer it. Remember to be concise. Now provide detailed advice on the following query:\n" + prompt;

        // Manually construct the JSON string
        return "{"
                + "\"model\": \"" + model + "\","
                + "\"messages\": ["
                + "    {"
                + "        \"role\": \"user\","
                + "        \"content\": \"" + escapeJson(customPrompt) + "\""
                + "    }"
                + "]"
                + "}";
    }

    private String escapeJson(String text) {
        return text.replace("\"", "\\\"")
                .replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public String extractMessageFromJSONResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray choices = jsonResponse.getJSONArray("choices");
        JSONObject message = choices.getJSONObject(0).getJSONObject("message");
        return message.getString("content");
    }
}
