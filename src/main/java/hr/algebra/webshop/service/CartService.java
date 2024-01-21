package hr.algebra.webshop.service;

import hr.algebra.webshop.dto.CartDTO;

import java.util.Optional;

public interface CartService {
    void createCart(CartDTO cartItemDTO);

    void updateCart(String id, CartDTO cartItemDTO);

    void deleteCart(String id);

    CartDTO getCartById(String id);

    Optional<CartDTO> getCartByUserName(String id);

    void deleteCartItemFromCart(String cartId, String cartItemId);
}
