package com.bethunter.bethunter_api.infra.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service("openAiAPIService")
public class CallApi {

    private final String apiKey;
    private final String url;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CallApi() {
        this.apiKey = System.getenv("OPENAI_KEY");
        this.url = "https://api.openai.com/v1/chat/completions";
//        super(System.getenv("OPENAI_KEY"), "https://api.openai.com/v1/chat/completions", "ChatGPT");
    }

    public String getAnalysisOfText(String text) throws IOException, InterruptedException {

        final String json = getPostJsonModelOpenAI(text);

        String jsonResponseString = callOpenAiAPI(json).replace("```json", "").replace("```", "");
        JsonNode rootNode = objectMapper.readTree(jsonResponseString);
        String principalMessage = rootNode.path("choices").get(0).path("message").path("content").asText();
        String modelResponse = rootNode.path("model").asText();
        int totalTokens = rootNode.path("usage").path("total_tokens").asInt();


        return modelResponse;
    }

    private String callOpenAiAPI (String json) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + this.apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
//        if (response.statusCode() == 200) {
//            return response.body();
//        } else {
//            System.out.println("Erro de comunicação com o modelo");
//        }
    }

    private String getPostJsonModelOpenAI(String text) throws JsonProcessingException {

        return "{\n" +
                "  \"model\": " + "gpt-4o-mini" + ",\n" +
                "  \"messages\": [\n" +
                "    {\"role\": \"system\", \"content\":  "  + "asdasdsaad" + " },\n" +
                "    {\"role\": \"user\", \"content\": " + objectMapper.writeValueAsString(text) + "}\n" +
                "  ],\n" +
                "  \"temperature\": 0,\n" +
                "  \"n\": 1,\n" +
                "  \"presence_penalty\": 0.0,\n" +
                "  \"frequency_penalty\": 0.0\n" +
                "}";
    }

}