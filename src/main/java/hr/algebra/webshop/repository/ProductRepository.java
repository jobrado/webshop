package hr.algebra.webshop.repository;

import hr.algebra.webshop.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
