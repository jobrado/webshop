package hr.algebra.webshop.entity;

import hr.algebra.webshop.enums.Delivery;
import hr.algebra.webshop.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@AllArgsConstructor
@Data
public class Order {
    @Id
    private String id;
    private Cart cart;
    private Delivery delivery;
    private PaymentMethod paymentMethod;
    private LocalDateTime date;

}
