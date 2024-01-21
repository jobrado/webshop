package hr.algebra.webshop.dto;

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
    private PhotoDTO productPhotos;
    private double price;
    private int quantityOnStock;
    private int quantityInCart;
    private boolean isAvailable;

}
