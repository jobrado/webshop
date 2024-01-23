package hr.algebra.webshop.controller;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.Util;
import hr.algebra.webshop.dto.UserDTO;
import hr.algebra.webshop.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @PostMapping("/registerNewUser")
    public String createUser(@ModelAttribute("userDTO") @Valid UserDTO userDTO,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors() || userDoesNotExist(userDTO.getEmail())) {
            return "redirect:/user/showFormRegisterUser?fail";
        } else {
            userService.createUser(userDTO);
            return "redirect:/admin/";
        }

    }

    private boolean userDoesNotExist(String email) {
        try {
            userService.getUserByEmail(email);
        } catch (ResourceNotFoundException e) {
            return false;
        }
        return true;
    }


    @GetMapping("/showFormRegisterUser")
    public String showFormRegisterUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "registerUser";
    }

    @GetMapping("/showLogin.html")
    public String loginPage(Model model, Authentication authentication) {
        model.addAttribute("user", new UserDTO());
        Util.addRoleToNavBar(authentication,  model);

        return "login";
    }


}