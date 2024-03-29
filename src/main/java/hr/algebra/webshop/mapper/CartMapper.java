package hr.algebra.webshop.mapper;

import hr.algebra.webshop.dto.CartDTO;
import hr.algebra.webshop.entity.Cart;

public class CartMapper {
    public static CartDTO mapToCartItemDTO (Cart cartItem) {
        return new CartDTO(
                cartItem.getId(),
                cartItem.getCartItem(),
                cartItem.getUser(),
                cartItem.getTotalPrice()

        );
    }
    public static Cart mapToCartItem(CartDTO cartItemDTO) {
        return new Cart(
                cartItemDTO.getId(),
                cartItemDTO.getCartItem(),
                cartItemDTO.getUser(),
                cartItemDTO.getTotalPrice()

        );
    }
}
