package hr.algebra.webshop.controller;

import hr.algebra.webshop.dto.OrderDTO;
import hr.algebra.webshop.service.PayPalService;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/paypal")
public class PayPalController {

    private final PayPalService payPalService;
    @PostMapping("/createOrder")
    public Object createOrder(@RequestParam OrderDTO orderDTO) {
        Double totalPrice = orderDTO.getCart().getTotalPrice();
        if (totalPrice == null || totalPrice <= 0) {
            return ResponseEntity.badRequest().body("Invalid total price");
        }

        try {
            return payPalService.createOrder(totalPrice);
        } catch (JSONException e) {

            return "Failed to create order.";
        }
    }



    @PostMapping("/capturePayment")
    public Object capturePayment(String orderId) {
        try {
            return payPalService.capturePayment(orderId);
        } catch (JSONException e) {

            return "Failed to capture payment.";
        }
    }
}