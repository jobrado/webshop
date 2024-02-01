package hr.algebra.webshop.dto;

import hr.algebra.webshop.entity.CartItem;
import hr.algebra.webshop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Set;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class CartDTO implements Serializable {

    private String id;
    private Set<CartItem> cartItem;
    private User user;
    private Double totalPrice;

    public CartDTO(User user) {
        this.user = user;
    }
}

