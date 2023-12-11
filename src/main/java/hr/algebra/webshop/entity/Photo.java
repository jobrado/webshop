package hr.algebra.webshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "photos")
public class Photo {
    @Id
    private String id;
    private String title;
    private Binary image;

    public Photo(String title) {
        this.title = title;
    }
}