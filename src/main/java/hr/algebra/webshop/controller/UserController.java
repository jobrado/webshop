package hr.algebra.webshop.controller;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.dto.UserDTO;
import hr.algebra.webshop.service.UserService;
import hr.algebra.webshop.serviceImplementation.UserDetailsServiceImpl;
import jakarta.servlet.Registration;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/registerNewUser")
    public String createUser(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult)
    {
        try {
        userService.getUserByEmail(userDTO.getEmail());
        userService.createUser(userDTO);
        return "redirect:/login?success";
    } catch (ResourceNotFoundException e) {
            bindingResult.reject("email", "There is already blabla");
        }
        return "redirect:/registerUser?fail";
    }



    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("user") UserDTO userDTO){
      try{
        UserDTO user = userService.getUserByEmail(userDTO.getEmail());
        return "redirect:/login?success";}
      catch (ResourceNotFoundException e) {

      }
        return "redirect:/login?success";
    }


    @GetMapping("/showFormRegisterUser")
    public String showFormRegisterUser(Model model){
        model.addAttribute("user", new UserDTO());
        return "registerUser";
    }
    @GetMapping("/showLogin")
    public String loginPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";
    }

}