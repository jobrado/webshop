package hr.algebra.webshop.controller;
import hr.algebra.webshop.service.PayPalService;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/paypal")
public class PayPalController {

    private final PayPalService payPalService;
    @PostMapping("/createOrder")
    public Object createOrder(@RequestParam double totalPrice) {
        if (totalPrice <= 0) {
            return "/customer/cart";
        }

        try {
            return payPalService.createOrder(totalPrice);
        } catch (JSONException e) {

            return "Failed to create order.";
        }
    }



    @PostMapping("/capturePayment/{orderId}/capture")
    public String capturePayment(@PathVariable String  orderId) {
        try {
            payPalService.capturePayment(orderId) ;
            return "localhost:8080/customer/allProducts.html";
        } catch (JSONException e) {

            return "Failed to capture payment.";
        }
    }
}