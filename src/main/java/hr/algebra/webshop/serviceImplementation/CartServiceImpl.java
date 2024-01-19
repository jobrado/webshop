package hr.algebra.webshop.serviceImplementation;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.dto.CartDTO;
import hr.algebra.webshop.entity.Cart;
import hr.algebra.webshop.mapper.CartMapper;
import hr.algebra.webshop.repository.CartRepository;
import hr.algebra.webshop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    @Override
    public void createCart(CartDTO cartItemDTO) {
        cartRepository.save(CartMapper.mapToCartItem(cartItemDTO));
    }

    @Override
    public void updateCart(String id, CartDTO cartItemDTO) {
        Cart cart = cartRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Cart doesnt exist"));
        cart.setTotalPrice(cartItemDTO.getTotalPrice());
        cart.setProduct(cartItemDTO.getProduct());

        cartRepository.save(cart);


    }

    @Override
    public void deleteCart(String id) {
        cartRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("cart not found with that id"));
        cartRepository.deleteById(id);
    }

    @Override
    public CartDTO getCartById(String id) {
        return CartMapper.mapToCartItemDTO(cartRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("cart not found with that id")));
    }

    @Override
    public CartDTO getCartByUserName(String email) {
        return cartRepository.findAll().stream()
                .filter(cart -> email.equals(cart.getUser().getEmail()))
                .findFirst()
                .map(CartMapper::mapToCartItemDTO).orElse(new CartDTO());
    }
}
