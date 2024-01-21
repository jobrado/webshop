package hr.algebra.webshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("product")
public class Product {
    @Id
    private String _id;
    private String name;
    @DBRef
    private Category category;
    private String description;
    private Photo productPhotos;
    private double price;
    private int quantityOnStock;
    private int quantityInCart;
    private boolean isAvailable;
}
