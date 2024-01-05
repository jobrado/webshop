package hr.algebra.webshop.dto;

import hr.algebra.webshop.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
    private String _id;
    private String name;
    private CategoryDTO category;
    private String description;
    private Photo productPhotos;
    private double price;
    private int quantity;
}
