package hr.algebra.webshop.serviceImplementation;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.dto.UserDTO;
import hr.algebra.webshop.enums.UserRole;
import hr.algebra.webshop.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class AdminServiceImpl {
    private UserService userService;

    @PostConstruct
    public void initializeAdmin() {
        String adminEmail = "admin@admin.admin";
        String adminPassword ="admin";
        try{
            userService.getUserByEmail(adminEmail);
        }
        catch (ResourceNotFoundException e){
        UserDTO adminUser = new UserDTO();
        adminUser.setFirstName("Admin");
        adminUser.setLastName("Adminovic");
        adminUser.setEmail(adminEmail);
        adminUser.setPassword(adminPassword);
        adminUser.setRole(Collections.singleton(UserRole.ADMIN));
        userService.createUser(adminUser);
}
    }
}
