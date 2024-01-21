package hr.algebra.webshop.dto;

import hr.algebra.webshop.entity.CartItem;

import hr.algebra.webshop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class CartDTO {
    private String id;
    private List<CartItem> cartItem;
    private User user;
    private Double totalPrice;

    public CartDTO(User user) {
        this.user = user;
    }
}

