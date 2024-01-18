package hr.algebra.webshop.controller;

import hr.algebra.webshop.dto.CartDTO;
import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.service.CartService;
import hr.algebra.webshop.service.CategoryService;
import hr.algebra.webshop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String showProductDetails(@PathVariable("id") String id,  Model model ){
        model.addAttribute("product", productService.getProductById(id));

        return "customer/productDetail";
    }
    @GetMapping("/allProducts/{id}")
    public String showProductsByCategoryId(@PathVariable("id") String id, Model model) {
        List<ProductDTO> productList = productService.getProductsByCategory_id(id);
        model.addAttribute("products", productList);
        model.addAttribute("categories", categoryService.getAllCategories());

        return "customer/productList";
    }


    @GetMapping("/cart/{email}")
    public String showCart(@PathVariable String email,Model model){
        Optional<CartDTO> cartDTO = cartService.getCartByUserName(email);
       if(cartDTO.isPresent()){
        model.addAttribute("cart", cartDTO.get());
       }
       else{
           return "redirect:customer/cart?no_products";
       }
        return "customer/cart";
    }



}
