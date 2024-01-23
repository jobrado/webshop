package hr.algebra.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {
    @GetMapping("/")
    private  String showMainPage(){
        return  "redirect:/customer/allProducts.html";
    }
}
