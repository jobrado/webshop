package hr.algebra.webshop.service;

import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.dto.UserDTO;
import hr.algebra.webshop.repository.ProductRepository;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(String id, ProductDTO productDTO);
    void deleteProduct(String id);
    ProductDTO getProductById(String id);
    List<ProductDTO> getAllProducts();
}
