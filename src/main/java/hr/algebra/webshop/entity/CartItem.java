package hr.algebra.webshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

@Document
@AllArgsConstructor
@Data
public class CartItem {
    @Id
    private String id;
    @DBRef
    private Map<Product,Integer> product;
    @DBRef
    private User user;
    private Integer totalPrice;

}
