package hr.algebra.webshop.dto;

import hr.algebra.webshop.enums.Delivery;
import hr.algebra.webshop.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String id;
    private CartDTO cart;
    private Delivery delivery;
    private PaymentMethod paymentMethod;
    private LocalDateTime date;
}
