package hr.algebra.webshop.serviceImplementation;


import hr.algebra.webshop.entity.UserRole;
import hr.algebra.webshop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import hr.algebra.webshop.entity.User;

import java.util.*;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if(user.isPresent()){
            List<GrantedAuthority> roles = getUserAuthority(user.get().getRole());
            return buildUserForAuthentication(user.get(), roles);
        }
        else
        {throw new UsernameNotFoundException(username);}
    }

    private List<GrantedAuthority> getUserAuthority (Set<UserRole> userRoleSet){
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoleSet.forEach((role) -> roles.add(new SimpleGrantedAuthority((role.name()))));
        return new ArrayList<>(roles);
    }
    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities){
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
