package hr.algebra.webshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("product")
public class Product {
    @Id
    @Field("id")
    private ObjectId id;
    private String name;
    private Category category;
    private String description;
    private Photo productPhotos;
    private double price;
    private int quantity;
}
