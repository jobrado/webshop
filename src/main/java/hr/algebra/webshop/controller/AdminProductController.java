package hr.algebra.webshop.controller;

import hr.algebra.webshop.Util;
import hr.algebra.webshop.dto.CategoryDTO;
import hr.algebra.webshop.dto.OrderDTO;
import hr.algebra.webshop.dto.PhotoDTO;
import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.service.*;
import lombok.AllArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminProductController {
    public PhotoService photoService;
    public UserService userService;
    public ProductService productService;
    public CategoryService categoryService;
    public OrderService orderService;

    @GetMapping("/")
    public String viewHomePage(Model model, Authentication authentication) {

        return findPage(1, model, "price", "asc", authentication);
    }

    @GetMapping("/showFormForCreateNewProduct")
    public String createNewProduct(Model model, Authentication authentication) {
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", categoryDTOS);
        Util.addRoleToNavBar(authentication, model);
        return "new_product";

    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") ProductDTO product, @RequestParam("image") MultipartFile file) throws IOException {
        convertFileToBase(product, file);
        productService.createProduct(product);

        return "redirect:/admin/";
    }

    @GetMapping("/showFormForUpdateProduct/{id}")
    public String showUpdateForm(@PathVariable String id,
                                 Model model, Authentication authentication) {
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryDTOS);
        Util.addRoleToNavBar(authentication, model);
        return "update_product";
    }

    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable String id,
                                @ModelAttribute("product") ProductDTO product,
                                @RequestParam("image") MultipartFile file) throws IOException {
        convertFileToBase(product, file);
        productService.updateProduct(id, product);

        return "redirect:/admin/";
    }

    private void convertFileToBase(@ModelAttribute("product") ProductDTO product, @RequestParam("image") MultipartFile file) throws IOException {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

        PhotoDTO photo = new PhotoDTO();
        photo.setOriginalFileName(base64Image);
        photo.setTitle("Your Title");
        photo.setImage(new Binary(file.getBytes()));
        photo.setContentType(file.getContentType());
        product.setProductPhotos(photo);
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable String id, @ModelAttribute("product") ProductDTO product) {
        photoService.deletePhoto(id);
        productService.deleteProduct(id);
        return "redirect:/admin/";
    }

    @GetMapping("/listOfUsers")
    public String viewListOfUsers(Model model, Authentication authentication) {
        model.addAttribute("users", userService.getAllUsers());

        Util.addRoleToNavBar(authentication, model);
        return "listOfUsers";

    }

    @GetMapping("/listOfAllOrders")
    public String viewListOfAllOrders(Model model, Authentication authentication) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("users", userService.getAllUsers());

        Util.addRoleToNavBar(authentication, model);
        return "listOfAllOrders";

    }

    @PostMapping("/filterThroughOrderList")
    public String filterThroughOrderList(@RequestParam(value = "email", required = false) String email,
                                         @RequestParam(value = "startDate", required = false) String startDate,
                                         @RequestParam(value = "endDate", required = false) String endDate,
                                         Model model, Authentication authentication) {
        if (email.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()) {
            model.addAttribute("orders", orderService.getOrdersByDate(LocalDate.parse(startDate).atStartOfDay(), LocalDate.parse(endDate).atStartOfDay()));
        }
        if (!email.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()) {
            List<OrderDTO> orders = orderService.getAllOrders().stream()
                    .filter(order -> (order.getCart().getUser().getEmail().equals(email))
                            && (!order.getDate().isBefore(LocalDate.parse(startDate).atStartOfDay()))
                            && (!order.getDate().isAfter(LocalDate.parse(endDate).atStartOfDay())))
                    .collect(Collectors.toList());
            model.addAttribute("orders", orders);
        }
        if (!email.isEmpty() && startDate.isEmpty() && endDate.isEmpty()) {
            model.addAttribute("orders", orderService.getAllOrdersByUserId(email));
        }

        model.addAttribute("users", userService.getAllUsers());
        Util.addRoleToNavBar(authentication, model);

        return "listOfAllOrders";

    }


    @GetMapping("/page/{pageNo}")
    public String findPage(@PathVariable(value = "pageNo") int pageNo,
                           Model model,
                           @RequestParam("sortField") String sortField,
                           @RequestParam("sortDirection") String sortDirection,
                           Authentication authentication) {
        int pageSize = 5;
        Page<ProductDTO> page = productService.findPaginated(pageNo, pageSize, sortField, sortDirection);
        List<ProductDTO> productDTOList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listProducts", productDTOList);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        Util.addRoleToNavBar(authentication, model);
        return "index";
    }


}
