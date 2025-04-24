package com.pranjal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class EmailService {

    @Value("${gemini.api.url}")
    private String geminiUrl;

    @Value("${gemini.api.key}")
    private String geminiKey;

    private final WebClient webClient;

    public EmailService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    private String fetchApiResponse(String prompt){
        Map<String, Object> requestBody = Map.of("contents", new Object[]{
                Map.of("parts", new Object[]{
                        Map.of("text", prompt)
                })
        });

        String response = webClient.post()
                .uri(geminiUrl+geminiKey)
                .header("Content-Type","application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }

    public String generateResponse(String email, String tone) {
        String prompt = "You are an email assistant. Based on the following input email and tone, generate only the response email text. \n" +
                "Do not include any explanations, introductions, or summaries and should be 125–200 words" +
                "— only return the final email message.\n" +
                "Input Email:\n" + email +
                "Desired Tone: " + tone +
                "Instructions:\n" +
                "1. Understand the context and intent of the original email.\n" +
                "2. Maintain the appropriate tone as specified.\n" +
                "3. Keep the response coherent, relevant, and grammatically correct.\n" +
                "4. Keep the message polite and professional (unless otherwise specified).\n" +
                "Your Response:";

        return fetchApiResponse(prompt);
    }

    public String generateEmail(String description, String tone) {
        String prompt = "You are an email assistant. Based on the following input email and tone," +
                " generate only the response email text. \n" +
                "Do not include any explanations, introductions, or summaries and should be " +
                "125–200 words if not in the description" +
                "— only return the final email message.\n" +
                "Description:\n" + description +
                "Desired Tone: " + tone +
                "Instructions:\n" +
                "1. Understand the context and intent of the description.\n" +
                "2. Maintain the appropriate tone as specified.\n" +
                "3. Keep the email coherent, relevant, and grammatically correct.\n" +
                "4. Keep the message polite and professional (unless otherwise specified).\n" +
                "Your Response:";

        return fetchApiResponse(prompt);
    }

    public String generateSummary(String email){
        String prompt = "You are an email assistant. Based on the following input email," +
                " generate only the summary text. \n" +
                "Do not include any explanations, introductions, or summaries and should be 125–200 words" +
                "— only return the final message.\n" +
                "Input Email:\n" + email +
                "Instructions:\n" +
                "1. Understand the context and intent of the original email.\n" +
                "2. Keep the response coherent, relevant, and grammatically correct.\n" +
                "Your Response:";

        return fetchApiResponse(prompt);
    }
}
