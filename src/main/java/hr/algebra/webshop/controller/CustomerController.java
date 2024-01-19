package hr.algebra.webshop.controller;

import hr.algebra.webshop.dto.CartDTO;
import hr.algebra.webshop.dto.CartProduct;
import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.entity.Cart;
import hr.algebra.webshop.service.CartService;
import hr.algebra.webshop.service.CategoryService;
import hr.algebra.webshop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/customer")
public class CustomerController {
    private ProductService productService;
    private CategoryService categoryService;
    private CartService cartService;

    @GetMapping("/allProducts.html")
    public String showProducts(Model model, Authentication authentication) {

        List<ProductDTO> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        model.addAttribute("categories", categoryService.getAllCategories());
        
        return "customer/productList";
    }
    @GetMapping("/showProductDetails/{id}")
    public String showProductDetails(@PathVariable("id") String id,  Model model, Authentication authentication ){
       // model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("cartProduct", new CartProduct( cartService.getCartByUserName(authentication.getName()), productService.getProductById(id)));
        return "customer/productDetail";
    }
    @GetMapping("/allProducts/{id}")
    public String showProductsByCategoryId(@PathVariable("id") String id, Model model) {
        List<ProductDTO> productList = productService.getProductsByCategory_id(id);
        model.addAttribute("products", productList);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "customer/productList";
    }

    @GetMapping("/cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String showCart(Model model, Authentication authentication){
        CartDTO cartDTO = cartService.getCartByUserName(authentication.getName());
        model.addAttribute("cart", cartDTO);


       //    return "redirect:customer/cart?no_products";

        return "customer/cart";
    }

@PostMapping("/addProductToCart")
public String addProductToCart(@ModelAttribute("cartProduct") CartProduct cartProduct){
    CartDTO cartDTO = cartProduct.getCartDTO();


        return "/customer/cart";
}



}
