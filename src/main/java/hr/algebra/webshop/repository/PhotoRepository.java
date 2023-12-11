package hr.algebra.webshop.repository;

import hr.algebra.webshop.entity.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo,String> {
}
