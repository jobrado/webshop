package hr.algebra.webshop.repository;

import hr.algebra.webshop.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
}
