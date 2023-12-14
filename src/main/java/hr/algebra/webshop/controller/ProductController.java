package hr.algebra.webshop.controller;

import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.service.PhotoService;
import hr.algebra.webshop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Controller
public class ProductController {
    public PhotoService photoService;
    public ProductService productService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
       return findPage(1, model);
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
    @GetMapping("/page/{pageNo}")
    public String findPage(@PathVariable (value = "pageNo") int pageNo, Model model){
        int pageSize = 1;
        Page<ProductDTO> page = productService.findPaginated(pageNo, pageSize);
        List<ProductDTO> productDTOList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listProducts", productDTOList);
        return "index";
    }
}
