package hr.algebra.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "hr.algebra")

public class WebShopApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(WebShopApplication.class, args);
    }
}
