package hr.algebra.webshop.controller;

import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.entity.Category;
import hr.algebra.webshop.entity.Photo;
import hr.algebra.webshop.entity.Product;
import hr.algebra.webshop.service.PhotoService;
import hr.algebra.webshop.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Controller
public class ProductController {
    public PhotoService photoService;
    public ProductService productService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listProducts", productService.getAllProducts());

        return "index";
    }

    @GetMapping("/showFormForCreateNewProduct")
    public String createNewProduct(Model model) {
        model.addAttribute("product", new ProductDTO());

        return "new_product";

    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") ProductDTO product) throws IOException {
        productService.createProduct(product);

        return "redirect:/";
    }

    @GetMapping("/showFormForUpdateProduct/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.getProductById(id));

        return "update_product";
    }

    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable String id, @ModelAttribute("product") ProductDTO product) throws IOException {
        productService.updateProduct(id,product);

        return "redirect:/";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable String id, @ModelAttribute("product") ProductDTO product){
        productService.deleteProduct(id);
        return "redirect:/";
    }
}
