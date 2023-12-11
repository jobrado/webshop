package hr.algebra.webshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("product")
public class Product {
    @Id
    private int id;
    private String name;
    private Category category;
    private String description;
    private Photo productPhotos;
    private double price;
    private int quantity;
}
