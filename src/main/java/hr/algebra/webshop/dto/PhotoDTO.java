package hr.algebra.webshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhotoDTO {
    private String id;
    private String title;
    private Binary image;
}
