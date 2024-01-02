package hr.algebra.webshop.service;

import hr.algebra.webshop.dto.UserDTO;


import java.util.List;
public interface UserService{
        UserDTO createUser(UserDTO user);
        UserDTO updateUser(String id, UserDTO user);
        void deleteUser(String id);
        UserDTO getUserByEmail(String email);
        UserDTO getUserById(String id);
        List<UserDTO> getAllUsers();
}
