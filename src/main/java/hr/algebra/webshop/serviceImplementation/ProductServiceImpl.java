package hr.algebra.webshop.serviceImplementation;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.entity.Product;
import hr.algebra.webshop.mapper.ProductMapper;
import hr.algebra.webshop.repository.ProductRepository;
import hr.algebra.webshop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    public ProductRepository productRepository;
    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product savedProduct = this.productRepository.save(ProductMapper.mapToProduct(productDTO));
        return ProductMapper.mapToProductDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Product does not exist with a given id"));

        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setDescription(productDTO.getDescription());
        product.setProductPhotos(productDTO.getProductPhotos());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());

        return ProductMapper.mapToProductDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Product does not exist with a given id"));
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO getProductById(String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ProductMapper::mapToProductDTO).orElseThrow(()
                -> new ResourceNotFoundException("Product does not exist with a given id"));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper::mapToProductDTO).collect(Collectors.toList());
    }
}
