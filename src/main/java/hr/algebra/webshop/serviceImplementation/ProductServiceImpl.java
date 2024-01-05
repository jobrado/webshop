package hr.algebra.webshop.serviceImplementation;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.dto.CategoryDTO;
import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.entity.Product;
import hr.algebra.webshop.mapper.CategoryMapper;
import hr.algebra.webshop.mapper.PhotoMapper;
import hr.algebra.webshop.mapper.ProductMapper;
import hr.algebra.webshop.repository.ProductRepository;
import hr.algebra.webshop.service.CategoryService;
import hr.algebra.webshop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    public ProductRepository productRepository;
    public CategoryService categoryService;
    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        CategoryDTO category = categoryService.getCategoryById(productDTO.getCategory().get_id());
        productDTO.setCategory(category);
        Product savedProduct = this.productRepository.save(ProductMapper.mapToProduct(productDTO));

        return ProductMapper.mapToProductDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Product does not exist with a given id"));

        product.setName(productDTO.getName());
        product.setCategory(CategoryMapper.mapToCategory(productDTO.getCategory()));
        product.setDescription(productDTO.getDescription());
        product.setProductPhotos(PhotoMapper.mapToPhoto(productDTO.getProductPhotos()));
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

    @Override
    public Page<ProductDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        return productRepository.findAll(PageRequest.of(pageNo - 1,pageSize, sort)).map(ProductMapper::mapToProductDTO);
    }
}
