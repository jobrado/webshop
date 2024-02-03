package hr.algebra.webshop.service;

import org.json.JSONException;

public interface PayPalService {
     public String generateAccessToken() throws JSONException;
     String createOrder(double orderDTO) throws JSONException;
     void capturePayment(String orderId) throws JSONException;
}
