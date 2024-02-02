package hr.algebra.webshop.mapper;

import hr.algebra.webshop.dto.UserDTO;
import hr.algebra.webshop.entity.User;

public class UserMapper {
    public static UserDTO mapToUserDTO (User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.isTheAccActive(),
                user.getRole()
        );
    }
    public static User mapToUser(UserDTO userDTO) {
        return new User(
                userDTO.getId(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.isTheAccActive(),
                userDTO.getRole()
        );
    }
}
