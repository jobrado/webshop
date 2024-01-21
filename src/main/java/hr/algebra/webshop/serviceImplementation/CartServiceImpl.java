package hr.algebra.webshop.serviceImplementation;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.dto.CartDTO;
import hr.algebra.webshop.entity.Cart;
import hr.algebra.webshop.mapper.CartMapper;
import hr.algebra.webshop.repository.CartItemRepository;
import hr.algebra.webshop.repository.CartRepository;
import hr.algebra.webshop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

    @Override
    public void createCart(CartDTO cartDTO) {
        cartItemRepository.saveAll(cartDTO.getCartItem());
        cartRepository.save(CartMapper.mapToCartItem(cartDTO));
        System.out.println(CartMapper.mapToCartItem(cartDTO));
    }

    @Override
    public void updateCart(String id, CartDTO cartDTO) {
        Cart cart = cartRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Cart doesnt exist"));
        cart.setTotalPrice(cartDTO.getTotalPrice());
        cartItemRepository.deleteAll(cart.getCartItem());
        cartItemRepository.saveAll(cartDTO.getCartItem());
        cart.setCartItem(cartDTO.getCartItem());
        cartRepository.save(cart);
        System.out.println(cart);


    }

    @Override
    public void deleteCart(String id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("cart not found with that id"));
        cartItemRepository.deleteAll(cart.getCartItem());
        cartRepository.deleteById(id);
    }

    @Override
    public CartDTO getCartById(String id) {
        return CartMapper.mapToCartItemDTO(cartRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("cart not found with that id")));
    }

    @Override
    public Optional<CartDTO> getCartByUserName(String email) {
        return cartRepository.findAll().stream()
                .filter(cart -> email.equals(cart.getUser().getEmail()))
                .findFirst()
                .map(CartMapper::mapToCartItemDTO);
    }

    @Override
    public void deleteCartItemFromCart(String cartId, String cartItemId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()
                -> new ResourceNotFoundException("cart not found with that id"));
        cartItemRepository.deleteById(cartItemId);
        cart.getCartItem().removeIf(cartItem -> cartItem.getId().equals(cartItemId));
        if (cart.getCartItem().isEmpty()) {
            cartRepository.deleteById(cartId);
        }
    }
}
