package hr.algebra.webshop.repository;

import hr.algebra.webshop.entity.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

interface CartItemRepository extends MongoRepository<CartItem, String> {
}
