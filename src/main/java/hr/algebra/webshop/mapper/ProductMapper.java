package hr.algebra.webshop.mapper;

import hr.algebra.webshop.dto.CategoryDTO;
import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.entity.Category;
import hr.algebra.webshop.entity.Product;

public class ProductMapper {
    public static ProductDTO mapToProductDTO (Product product) {
        CategoryDTO categoryDTO = CategoryMapper.mapToCategoryDTO(product.getCategory());
        return new ProductDTO(
                product.get_id(),
                product.getName(),
                categoryDTO,
                product.getDescription(),
                product.getProductPhotos(),
                product.getPrice(),
                product.getQuantity()
        );
    }
    public static Product mapToProduct(ProductDTO product) {
        Category category = CategoryMapper.mapToCategory(product.getCategory());
        return new Product(
                product.get_id(),
                product.getName(),
                category,
                product.getDescription(),
                product.getProductPhotos(),
                product.getPrice(),
                product.getQuantity()
        );
    }
}
