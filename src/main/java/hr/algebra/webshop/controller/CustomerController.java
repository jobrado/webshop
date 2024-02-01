package hr.algebra.webshop.controller;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.Util;
import hr.algebra.webshop.dto.CartDTO;
import hr.algebra.webshop.dto.OrderDTO;
import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.dto.UserDTO;
import hr.algebra.webshop.entity.CartItem;
import hr.algebra.webshop.entity.Product;
import hr.algebra.webshop.enums.Delivery;
import hr.algebra.webshop.enums.PaymentMethod;
import hr.algebra.webshop.mapper.ProductMapper;
import hr.algebra.webshop.mapper.UserMapper;
import hr.algebra.webshop.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Controller
@RequestMapping("/customer")
public class CustomerController {
    private ProductService productService;
    private CategoryService categoryService;
    private CartService cartService;
    private UserService userService;
    private OrderService orderService;

    @GetMapping("/allProducts.html")
    public String showProducts(Model model, Authentication authentication) {

        List<ProductDTO> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        model.addAttribute("categories", categoryService.getAllCategories());
        Util.addRoleToNavBar(authentication, model);
        return "customer/productList";
    }


    @GetMapping("/showProductDetails/{id}")
    public String showProductDetails(@PathVariable("id") String id,
                                     Model model,
                                     Authentication authentication) {
        model.addAttribute("product", productService.getProductById(id));
        Util.addRoleToNavBar(authentication, model);
        return "customer/productDetail";
    }

    @GetMapping("/allProducts/{id}")
    public String showProductsByCategoryId(@PathVariable("id") String id,
                                           Model model,
                                           Authentication authentication) {
        List<ProductDTO> productList = productService.getProductsByCategory_id(id);
        model.addAttribute("products", productList);
        model.addAttribute("categories", categoryService.getAllCategories());
        Util.addRoleToNavBar(authentication, model);
        return "customer/productList";
    }


    @PostMapping("/addProductToCart/{id}")
    public String addProductToCart(@PathVariable String id,
                                   @RequestParam("quantity") Integer quantity,
                                   Authentication authentication,
                                   HttpSession httpSession,
                                   Model model) {
        if (quantity > 0) {
            ProductDTO productDTO = productService.getProductById(id);
            CartItem cartItem = new CartItem(ProductMapper.mapToProduct(productDTO), quantity);
            cartItem.setId(productDTO.get_id());
            cartItem.setQuantity(quantity);
            if (authentication != null) {
                makeCartAsLoggedInUser(authentication, cartItem);
            } else {
                if (httpSession.getAttribute("userCart") != null) {
                    CartDTO existingCartInSession = (CartDTO) httpSession.getAttribute("userCart");
                    existingCartInSession.getCartItem().stream()
                            .filter(existingCartItem -> existingCartItem.getId().equals(cartItem.getId()))
                            .findFirst()
                            .ifPresentOrElse(
                                    existingCartItem -> existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity()),
                                    () -> existingCartInSession.getCartItem().add(cartItem)
                            );

          //         httpSession.setAttribute("userCartItems", existingCartInSession.getCartItem());
                    httpSession.setAttribute("userCart", existingCartInSession);
                } else {
                    Set<CartItem> cartItems = new HashSet<>();
                    cartItems.add(cartItem);
                    CartDTO newSessionCart = new CartDTO();
                    newSessionCart.setCartItem(cartItems);
                    newSessionCart.setTotalPrice(calculateTotalPrice(newSessionCart.getCartItem()));
                    httpSession.setAttribute("userCart", newSessionCart);
                }
            }
        } else {
            model.addAttribute("cartNotFound", true);
        }
        return "redirect:http://localhost:8080/customer/cart";
    }

    private void makeCartAsLoggedInUser(Authentication authentication,
                                        CartItem cartItem) {
        String username = authentication.getName();
        UserDTO user = userService.getUserByEmail(username);
        Optional<CartDTO> optionalCart = cartService.getCartByUserName(username);

        CartDTO cart = optionalCart.orElseGet(() -> createNewCart(user));

        cart.getCartItem().stream()
                .filter(existingCartItem -> existingCartItem.getId().equals(cartItem.getId()))
                .findFirst()
                .ifPresentOrElse(
                        existingCartItem -> existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity()),
                        () -> cart.getCartItem().add(cartItem)
                );


        cart.setTotalPrice(calculateTotalPrice(cart.getCartItem()));
        if (cart.getId() != null) {
            cartService.updateCart(cart.getId(), cart);
        } else {
            cartService.createCart(cart);
        }
    }

    private CartDTO createNewCart(UserDTO user) {
        CartDTO newCart = new CartDTO(UserMapper.mapToUser(user));
        newCart.setCartItem(new HashSet<>());
        return newCart;
    }

    private double calculateTotalPrice(Set<CartItem> cartItems) {
        if (!cartItems.isEmpty()) {
            return cartItems.stream()
                    .mapToDouble(cartItem -> {
                        Product product = cartItem.getProduct();
                        int quantity = cartItem.getQuantity();
                        double productPrice = product.getPrice();
                        return productPrice * quantity;
                    })
                    .sum();
        }
        return 0;
    }

    @GetMapping("/deleteProductFromCart/{id}")
    public String deleteProductFromCart(@PathVariable String id,
                                        @RequestParam String cartId,
                                        Authentication authentication,
                                        HttpSession session) {
        if (authentication != null) {
            cartService.deleteCartItemFromCart(cartId, id);
            try {
                CartDTO cartById = cartService.getCartById(cartId);
                cartById.setTotalPrice(calculateTotalPrice(cartById.getCartItem()));
                cartService.updateCart(cartId, cartById);
            } catch (ResourceNotFoundException e) {
                return "redirect:/customer/allProducts.html";
            }
        } else {
            CartDTO cartDTO = (CartDTO) session.getAttribute("userCart");
            if(cartDTO != null){
            cartDTO.getCartItem().removeIf(cartWithId -> cartWithId.getId().equals(id));
            cartDTO.setTotalPrice(calculateTotalPrice(cartDTO.getCartItem()));
            session.setAttribute("userCart", cartDTO);}

        }
        return "redirect:/customer/cart";

    }

    @GetMapping("/updateProductFromCart/{id}")
    public String updateProductFromCart(@PathVariable String id,
                                        @RequestParam String cartId,
                                        @RequestParam int quantity,
                                        Authentication authentication,
                                        HttpSession session) {
        if (quantity > 0) {
            if (authentication != null) {
                CartDTO cartById = cartService.getCartById(cartId);
                cartById.getCartItem()
                        .stream().filter(cartItem -> cartItem.getId().equals(id))
                        .findFirst()
                        .ifPresent(cartItem -> cartItem.setQuantity(quantity));
                cartById.setTotalPrice(calculateTotalPrice(cartById.getCartItem()));
                cartService.updateCart(cartId, cartById);
            } else {
                CartDTO cartDTO = (CartDTO) session.getAttribute("userCart");
                cartDTO.getCartItem()
                        .stream().filter(cartItem -> cartItem.getId().equals(id))
                        .findFirst()
                        .ifPresent(cartItem -> cartItem.setQuantity(quantity));
                cartDTO.setTotalPrice(calculateTotalPrice(cartDTO.getCartItem()));
                session.setAttribute("userCart", cartDTO);

            }
        }
        return "redirect:/customer/cart";

    }


    @GetMapping("/cart")
    public String showCart(Model model,
                           Authentication authentication,
                           HttpSession session) {
        if (authentication != null) {
            if (session.getAttribute("userCart") != null) {
                CartDTO cartFromSession = (CartDTO) session.getAttribute("userCart");
                Optional<CartDTO> cartFromDatabase = cartService.getCartByUserName(authentication.getName());
                if (cartFromDatabase.isPresent()) {
                    cartFromDatabase.get().getCartItem().addAll(cartFromSession.getCartItem());
                    cartService.updateCart(cartFromDatabase.get().getId(), cartFromDatabase.get());
                    model.addAttribute("cart", cartFromDatabase.get());
                } else {
                    cartFromSession.setUser(UserMapper.mapToUser(userService.getUserByEmail(authentication.getName())));

                    model.addAttribute("cart", cartService.createCart(cartFromSession));
                }
                session.invalidate();

            } else {
                Optional<CartDTO> cartFromDatabase = cartService.getCartByUserName(authentication.getName());
                cartFromDatabase.ifPresentOrElse(
                        cart -> model.addAttribute("cart", cart),
                        () -> model.addAttribute("cartNotFound", true)
                );
            }
        } else if (session.getAttribute("userCart") != null) {
            CartDTO cartFromSession = (CartDTO) session.getAttribute("userCart");

          /*  if (session.getAttribute("userCartItems") != null) {
                Set<CartItem> cartItems = (Set<CartItem>) session.getAttribute("userCartItems");
                cartFromSession.getCartItem().addAll(cartItems);
                session.removeAttribute("userCartItems");
                session.setAttribute("userCart", cartFromSession);
            }*/

            model.addAttribute("cart", cartFromSession);
        } else {
            model.addAttribute("cartNotFound", true);
        }

        Util.addRoleToNavBar(authentication, model);
        return "customer/cart";
    }

    @GetMapping("/chooseDeliveryAndPaymentMethod/{id}")
    public String chooseDeliveryAndPaymentMethod(Model model,
                                                 @PathVariable String id,
                                                 Authentication authentication) {

        CartDTO cartById = cartService.getCartById(id);
        model.addAttribute("cart", cartById);
        Util.addRoleToNavBar(authentication, model);

        return "customer/deliveryAndPaymentInfo";
    }

    @PostMapping("makeAnOrder/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String makeAnOrder(@RequestParam Delivery deliveryMethod,
                              @RequestParam PaymentMethod paymentMethod,
                              @PathVariable("id") String id,
                              Model model,
                              Authentication authentication) {
        CartDTO cartById = cartService.getCartById(id);
        OrderDTO order = new OrderDTO();
        order.setCart(cartById);
        order.setDelivery(deliveryMethod);
        order.setPaymentMethod(paymentMethod);
        order.setDate(LocalDateTime.now());

        orderService.createOrder(order);
        cartService.deleteCart(id);

        Util.addRoleToNavBar(authentication, model);
        model.addAttribute("order", order);

        if (paymentMethod == PaymentMethod.CASH) {
            return "customer/order";
        } else {
            return "customer/order_paypal";
        }
    }

    @GetMapping("/getOrderHistory")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String orderHistory(Model model,
                               Authentication authentication) {
        Util.addRoleToNavBar(authentication, model);
        model.addAttribute("orders", orderService.getAllOrdersByUserId(authentication.getName()));
        return "customer/orderHistory";
    }

    @GetMapping("/deleteCart/{id}")
    public String deleteCart(@PathVariable String id,
                             Authentication authentication,
                             HttpSession session,
                             Model model) {

        if (authentication != null) {
            cartService.deleteCart(id);
        } else {
            session.invalidate();
        }
        model.addAttribute("cartNotFound", true);
        return "customer/cart";
    }
}

