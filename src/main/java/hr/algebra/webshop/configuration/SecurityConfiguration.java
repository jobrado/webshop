package hr.algebra.webshop.configuration;

import hr.algebra.webshop.repository.UserRepository;
import hr.algebra.webshop.serviceImplementation.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(proxyTargetClass = true)
public class SecurityConfiguration {

    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeConfig -> authorizeConfig
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/page/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/updateProduct").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,  "/admin/updateProduct").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/listOfAllOrders").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/filterThroughOrderList").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/favicon.ico").permitAll()
                        .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
                        .requestMatchers( "/api/**").permitAll()
                        .requestMatchers("/api/paypal/createOrder").permitAll()
                        .requestMatchers("/customer/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/customer/**").permitAll()
)
                .formLogin((form) -> form
                        .loginPage("/user/showLogin.html")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/customer/allProducts.html", true)

                        .permitAll()

                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/user/showLogin.html"))

                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }

}






