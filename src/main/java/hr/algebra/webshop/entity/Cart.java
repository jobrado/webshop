package hr.algebra.webshop.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;


@Document
@AllArgsConstructor
@Data
public class Cart {
    @Id
    private String id;
    @DBRef
    private List<CartItem> cartItem;
    @DBRef
    private User user;
    private Double totalPrice;

}
