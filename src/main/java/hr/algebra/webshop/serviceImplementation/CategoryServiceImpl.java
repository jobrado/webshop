package hr.algebra.webshop.serviceImplementation;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.dto.CategoryDTO;
import hr.algebra.webshop.entity.Category;
import hr.algebra.webshop.mapper.CategoryMapper;
import hr.algebra.webshop.repository.CategoryRepository;
import hr.algebra.webshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    public CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category savedCategory = this.categoryRepository.save(CategoryMapper.mapToCategory(categoryDTO));

        return CategoryMapper.mapToCategoryDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(String id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Category does not exist with a given id"));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return CategoryMapper.mapToCategoryDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Category does not exist with a given id"));
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDTO getCategoryById(String id) {
        Optional<Category> category = categoryRepository.findById(id);

        return category.map(CategoryMapper::mapToCategoryDTO).orElseThrow(()
                -> new ResourceNotFoundException("Category does not exist with a given id"));

    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> category = categoryRepository.findAll();

        return category.stream().map(CategoryMapper::mapToCategoryDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        Category category = categoryRepository.findCategoryByName(name);

        return CategoryMapper.mapToCategoryDTO(category);
    }
}
