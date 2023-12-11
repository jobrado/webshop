package hr.algebra.webshop.mapper;

import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.dto.UserDTO;
import hr.algebra.webshop.entity.Product;
import hr.algebra.webshop.entity.User;

public class ProductMapper {
    public static ProductDTO mapToProductDTO (Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getDescription(),
                product.getProductPhotos(),
                product.getPrice(),
                product.getQuantity()
        );
    }
    public static Product mapToProduct(ProductDTO product) {
        return new Product(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getDescription(),
                product.getProductPhotos(),
                product.getPrice(),
                product.getQuantity()
        );
    }
}
