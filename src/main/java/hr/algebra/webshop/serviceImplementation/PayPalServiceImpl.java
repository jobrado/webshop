package hr.algebra.webshop.serviceImplementation;

import hr.algebra.webshop.service.PayPalService;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@NoArgsConstructor
@Service
public class PayPalServiceImpl implements PayPalService {

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.client-secret}")
    private String clientSecret;

    private final String BASE = "https://api-m.sandbox.paypal.com/";

    private String getAuth(String clientId, String clientSecret) {
        String auth = clientId + ":" + clientSecret;
        return Base64.getEncoder().encodeToString(auth.getBytes());
    }

    public String generateAccessToken() throws JSONException {
        String auth = this.getAuth(
                clientId,
                clientSecret
        );
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + auth);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        HttpEntity<?> request = new HttpEntity<>(requestBody, headers);
        requestBody.add("grant_type", "client_credentials");

        ResponseEntity<String> response = restTemplate.postForEntity(
                BASE +"/v1/oauth2/token",
                request,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return new JSONObject(response.getBody()).getString("access_token");
        } else {
            return "Unavailable to get ACCESS TOKEN, STATUS CODE " + response.getStatusCode();
        }
    }


    @Override
    public String createOrder(double totalPrice) throws JSONException {

        String accessToken = generateAccessToken();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // JSON String
        String requestJson = String.format("{\"intent\":\"CAPTURE\",\"purchase_units\":[{\"amount\":{\"currency_code\":\"USD\",\"value\":\"%s\"}}]}", totalPrice);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                BASE + "/v2/checkout/orders",
                entity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to create order, Status Code: " + response.getStatusCode());
        }
    }

    @Override
    public void capturePayment(String orderId) throws JSONException {
        String accessToken = generateAccessToken();
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                BASE + "/v2/checkout/orders/" + orderId + "/capture",
                HttpMethod.POST,
                entity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED) {
            response.getBody();
        } else {
            throw new RuntimeException("Failed to capture payment, Status Code: " + response.getStatusCode());
        }
    }
}

