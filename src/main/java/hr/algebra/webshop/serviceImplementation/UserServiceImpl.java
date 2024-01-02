package hr.algebra.webshop.serviceImplementation;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.dto.UserDTO;
import hr.algebra.webshop.entity.User;
import hr.algebra.webshop.entity.UserRole;
import hr.algebra.webshop.mapper.UserMapper;
import hr.algebra.webshop.repository.UserRepository;
import hr.algebra.webshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    public UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public UserDTO createUser(UserDTO userDTO) {
          userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
          userDTO.setRole(Collections.singleton(UserRole.USER));
          User savedUser = this.userRepository.save(UserMapper.mapToUser(userDTO));
          return UserMapper.mapToUserDTO(savedUser);
      }

    @Override
    public UserDTO updateUser(String id, UserDTO userDTO) {

        User user = userRepository.findById(id).orElseThrow(()
        -> new ResourceNotFoundException("User does not exist with a given id"));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        return UserMapper.mapToUserDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("User does not exist with a given id"));
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(UserMapper::mapToUserDTO).orElseThrow(()
                -> new ResourceNotFoundException("User does not exist with a given email"));
    }

    @Override
    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("User does not exist with a given id"));
        return UserMapper.mapToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::mapToUserDTO).collect(Collectors.toList());
    }

}
