package hr.algebra.webshop.configuration;

import hr.algebra.webshop.serviceImplementation.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                .authorizeHttpRequests(authorizeConfig -> {
                    authorizeConfig
                            .requestMatchers(HttpMethod.GET, "/user/showLogin").permitAll()
                                   .requestMatchers(HttpMethod.GET, "/user/showFormRegisterUser").permitAll()
                            .requestMatchers(HttpMethod.GET, "/").authenticated()
                            ;

                })

                .formLogin(form -> form
                        .loginPage("/user/showLogin"))
                .build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }}






