package hr.algebra.webshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.util.Set;

@Document
@AllArgsConstructor
@Data
public class Cart implements Serializable {
    @Id
    private String id;
    @DBRef
    private Set<CartItem> cartItem;
    @DBRef
    private User user;
    private Double totalPrice;

}
