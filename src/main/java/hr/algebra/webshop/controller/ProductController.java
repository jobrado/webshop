package hr.algebra.webshop.controller;

import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.entity.Photo;
import hr.algebra.webshop.service.PhotoService;
import hr.algebra.webshop.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RequestMapping("/product")
@Controller
public class ProductController {
    public PhotoService photoService;
    public ProductService productService;

    @PostMapping("/createNewProduct")
    public String createNewProduct(Model model, @RequestParam("photoTitle") String title, @RequestParam("file") MultipartFile file, @ModelAttribute @Valid ProductDTO product, BindingResult bindingResult) throws IOException {Photo photo = photoService.addPhoto(title, file);
       model.addAttribute("product", product);
        if (bindingResult.hasErrors()) {
            return "homePage";
        }
        else {
            product.setProductPhotos(photo);
            productService.createProduct(product);
            return "redirect:/homePage.html";
        }
    }



}
