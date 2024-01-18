package hr.algebra.webshop.dto;

import hr.algebra.webshop.entity.Product;
import hr.algebra.webshop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;
@AllArgsConstructor
@Data
public class CartDTO {
    private String id;
    private Map<Product,Integer> product;
    private User user;
    private Integer totalPrice;

}

