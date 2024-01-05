package hr.algebra.webshop.mapper;

import hr.algebra.webshop.dto.CategoryDTO;
import hr.algebra.webshop.entity.Category;

public class CategoryMapper {
    public static CategoryDTO mapToCategoryDTO (Category category) {
        return new CategoryDTO(
                category.get_id(),
                category.getName(),
                category.getDescription()
        );
    }
    public static Category mapToCategory(CategoryDTO category) {
        return new Category(
                category.get_id(),
                category.getName(),
                category.getDescription()
        );
    }
}
