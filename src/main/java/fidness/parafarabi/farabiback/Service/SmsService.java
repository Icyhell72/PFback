package fidness.parafarabi.farabiback.Service;

import com.fasterxml.jackson.databind.ObjectMapper; // Add Jackson library
import org.springframework.beans.factory.annotation.Value; // Add Value annotation
import org.springframework.http.*;
import org.springframework.stereotype.Service; // Add Service annotation
import org.springframework.web.reactive.function.client.WebClient; // Add WebClient class

import java.util.Map;

@Service // Mark this class as a service
public class SmsService {

    @Value("${client.id}") // Inject client id from application.properties
    private String clientId;

    @Value("${client.secret}") // Inject client secret from application.properties
    private String clientSecret;

    private final WebClient webClient; // Use WebClient instead of RestTemplate
    private final ObjectMapper objectMapper; // Use ObjectMapper to parse JSON

    public SmsService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public String getToken() {
        String tokenUrl = "https://api.orange.com/oauth/v3/token";

        // Use WebClient to make HTTP request
        String responseBody = webClient.post()
                .uri(tokenUrl)
                .headers(headers -> {
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    headers.setBasicAuth(clientId, clientSecret);
                })
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (responseBody != null) {
            // Use ObjectMapper to parse JSON response
            try {
                Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
                return (String) responseMap.get("access_token"); // Get access token as a String value
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse access token");
            }
        } else {
            throw new RuntimeException("Failed to obtain access token");
        }
    }

    public void sendSMS(String recipientEmail, String verificationCode) {
        String accessToken = getToken();

        String apiUrl = "https://api.orange.com/smsmessaging/v1/outbound/tel%3A%2B2160000/requests";

        // Use WebClient to make HTTP request
        String responseBody = webClient.post()
                .uri(apiUrl)
                .headers(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setBearerAuth(accessToken);
                })
                .bodyValue("{\"outboundSMSMessageRequest\": {\"address\": \"tel:+216" + recipientEmail + "\",\"senderAddress\": \"tel:+2160000\",\"outboundSMSTextMessage\": {\"message\": \"Bienvenue chez Para El Farabi Tunisie. Votre code de vérification de compte est: " + verificationCode + "\"}}}")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (responseBody != null) {
            System.out.println("SMS sent successfully: " + responseBody);
        } else {
            System.err.println("Error sending SMS: " + responseBody);
        }
    }
}
