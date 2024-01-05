package hr.algebra.webshop.service;

import hr.algebra.webshop.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(String id, CategoryDTO categoryDTO);
    void deleteCategory(String id);
    CategoryDTO getCategoryById(String id);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryByName(String name);

}
