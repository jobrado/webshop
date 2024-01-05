package hr.algebra.webshop.controller;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.dto.UserDTO;
import hr.algebra.webshop.entity.UserRole;
import hr.algebra.webshop.service.UserService;
import hr.algebra.webshop.serviceImplementation.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
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
     try{
        userService.getUserByEmail(email);}
     catch (ResourceNotFoundException e){
         return false;
     }
        return true;
    }


    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("user") UserDTO userDTO) {
        UserDetails userDetails = userDetailsService.checkIfPasswordMatches(userDTO.getEmail(), userDTO.getPassword());

            if (userDetails != null) {
               if( userDetails.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_"+UserRole.ADMIN.name())))
                {
                return "redirect:/admin/";
               }
                return "redirect:/";
            }
       else {
                return "redirect:/user/showLogin?fail";
            }
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