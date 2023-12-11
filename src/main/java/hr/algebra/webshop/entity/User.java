package hr.algebra.webshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("user")
public class User {
    @Id
    private String  id;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private String password;

    private UserRole role;
}
