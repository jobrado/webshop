package hr.algebra.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "hr.algebra")

public class WebShopApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebShopApplication.class, args);
    }

}
