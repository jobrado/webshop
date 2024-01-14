package hr.algebra.webshop.mapper;

import hr.algebra.webshop.dto.CartItemDTO;
import hr.algebra.webshop.entity.CartItem;

public class CartItemMapper {
    public static CartItemDTO mapToCartItemDTO (CartItem cartItem) {
        return new CartItemDTO(
                cartItem.getId(),
                cartItem.getProduct(),
                cartItem.getUser(),
                cartItem.getTotalPrice()

        );
    }
    public static CartItem mapToCartItem(CartItemDTO cartItemDTO) {
        return new CartItem(
                cartItemDTO.getId(),
                cartItemDTO.getProduct(),
                cartItemDTO.getUser(),
                cartItemDTO.getTotalPrice()

        );
    }
}
