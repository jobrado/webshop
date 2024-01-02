package hr.algebra.webshop.controller;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.dto.UserDTO;
import hr.algebra.webshop.service.UserService;
import hr.algebra.webshop.serviceImplementation.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/registerNewUser")
    public String createUser(@ModelAttribute("userDTO") @Valid UserDTO userDTO, BindingResult bindingResult) {

            if(bindingResult.hasErrors() || userDoesNotExist(userDTO.getEmail())){
                return "redirect:/user/showFormRegisterUser?fail";
            }
            else {
                userService.createUser(userDTO);
                return "redirect:/admin/";
            }

    }

    private boolean userDoesNotExist(String email) {
        UserDTO userByEmail = userService.getUserByEmail(email);
        return userByEmail != null;
    }


    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("user") UserDTO userDTO) {
        try {
           userService.getUserByEmail(userDTO.getEmail());
            return "redirect:/login?success";
        } catch (ResourceNotFoundException e) {

        }
        return "redirect:/login?success";
    }


    @GetMapping("/showFormRegisterUser")
    public String showFormRegisterUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "registerUser";
    }

    @GetMapping("/showLogin")
    public String loginPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";
    }

}