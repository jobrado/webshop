package hr.algebra.webshop.service;

import hr.algebra.webshop.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(String id, ProductDTO productDTO);
    void deleteProduct(String id);
    ProductDTO getProductById(String id);
    List<ProductDTO> getAllProducts();
    Page<ProductDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

     List<ProductDTO> getProductsByCategory_id(String id);

}
