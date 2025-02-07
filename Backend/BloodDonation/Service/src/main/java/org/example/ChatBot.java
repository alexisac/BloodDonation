package org.example;

import Utils.ServiceException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.io.IOError;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChatBot {

    private static final String API_KEY = "";
    private static final String API_URL = "";

    public String gptEndpoints(String prompt) throws ServiceException {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        try {
            JsonObject messageObject = new JsonObject();
            messageObject.addProperty("role", "user");
            messageObject.addProperty("content", prompt);

            JsonArray messagesArray = new JsonArray();
            messagesArray.add(messageObject);

            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", "gpt-3.5-turbo-0125");
            requestBody.add("messages", messagesArray);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(requestBody)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            JSONObject jsonObj = new JSONObject(response.body());
            return jsonObj.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
        } catch (IOException | URISyntaxException | InterruptedException ex) {
            throw new ServiceException("Error sending request: " + ex.getMessage());
        }
    }
}
