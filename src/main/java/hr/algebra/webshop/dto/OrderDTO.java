package hr.algebra.webshop.dto;

import hr.algebra.webshop.entity.Cart;
import hr.algebra.webshop.enums.Delivery;
import hr.algebra.webshop.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.format.DateTimeFormatter;
@Data
@AllArgsConstructor
public class OrderDTO {
    private String id;
    private Cart cart;
    private Delivery delivery;
    private PaymentMethod paymentMethod;
    private DateTimeFormatter date;
}
