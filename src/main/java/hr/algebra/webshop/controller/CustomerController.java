package hr.algebra.webshop.controller;

import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.service.CategoryService;
import hr.algebra.webshop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@AllArgsConstructor
@Controller
@RequestMapping("/customer")
public class CustomerController {
    private ProductService productService;
    private CategoryService categoryService;


    @GetMapping("/allProducts")
    public String showProducts(Model model) {
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

}
