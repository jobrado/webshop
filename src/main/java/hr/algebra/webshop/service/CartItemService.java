package hr.algebra.webshop.service;

import hr.algebra.webshop.dto.CartItemDTO;

import java.util.List;

public interface CartItemService {
    void createCartItem(CartItemDTO cartItemDTO);
    void updateCartItem(String id, CartItemDTO cartItemDTO);
    void deleteCartItemDTO(String id);
    CartItemDTO getCartItemById(String id);
    List<CartItemDTO> getAllCartItems();
}
