package hr.algebra.webshop.dto;

import hr.algebra.webshop.entity.Category;
import hr.algebra.webshop.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
    private ObjectId id;
    private String name;
    private Category category;
    private String description;
    private Photo productPhotos;
    private double price;
    private int quantity;
}
