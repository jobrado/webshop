package hr.algebra.webshop.controller;

import hr.algebra.webshop.dto.UserDTO;
import hr.algebra.webshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @PostMapping("registerNewUser.html")
    public String createDepartment(Model model, @ModelAttribute UserDTO userDTO){
        UserDTO user = userService.createUser(userDTO);
        return "redirect:/user/logInUser.html";
    }
}