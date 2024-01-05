package hr.algebra.webshop.repository;

import hr.algebra.webshop.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends  MongoRepository<Category, String>  {
    Category findCategoryByName(String name);
}
