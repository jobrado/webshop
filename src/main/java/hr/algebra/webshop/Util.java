package hr.algebra.webshop;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public class Util {
    public static void addRoleToNavBar(Authentication authentication, Model model){
        model.addAttribute("authentication", authentication);
        if(authentication != null){
            boolean role_admin = authentication.getAuthorities().toString().equals("[ROLE_ADMIN]");
            boolean role_user = authentication.getAuthorities().toString().equals("[ROLE_USER]");
            model.addAttribute("role_admin", role_admin);
            model.addAttribute("role_user", role_user);
        }
    }

}
