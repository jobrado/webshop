package hr.algebra.webshop.controller;

import hr.algebra.webshop.Util;
import hr.algebra.webshop.dto.CategoryDTO;
import hr.algebra.webshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {
    public CategoryService categoryService;

    @GetMapping("/")
    public String showAllCategories(Model model, Authentication authentication) {
        Util.addRoleToNavBar(authentication,  model);

        model.addAttribute("categories", categoryService.getAllCategories());
        return "category/listOfCategories";
    }

    @GetMapping("/showFormForCreatingCategory")
    public String showFormForCreatingCategory(Model model, Authentication authentication) {
        model.addAttribute("category", new CategoryDTO());
        Util.addRoleToNavBar(authentication,  model);

        return "category/create_category";
    }

    @PostMapping("saveCategory")
    public String saveCategory(@ModelAttribute("category") CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return "redirect:/admin/category/";

    }

    @GetMapping("/showFormForUpdateCategory/{id}")
    public String showFormForUpdateCategory(Model model, @PathVariable String id, Authentication authentication) {
        Util.addRoleToNavBar(authentication,  model);

        model.addAttribute("category", categoryService.getCategoryById(id));
        return "category/update_category";
    }

    @PostMapping("/updateCategory/{id}")
    public String updateProduct(@PathVariable String id, @ModelAttribute("category") CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return "redirect:/admin/category/";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@ModelAttribute("category") CategoryDTO categoryDTO, @PathVariable String id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/category/";
    }
}
