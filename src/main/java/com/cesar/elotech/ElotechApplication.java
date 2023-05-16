package com.cesar.elotech;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Teste - Elotech",
                contact = @Contact(
                        name = "Cesar Augusto",
                        email = "cesaragstincs@gmail.com"
                )
        )
)
public class ElotechApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElotechApplication.class, args);
    }

}
