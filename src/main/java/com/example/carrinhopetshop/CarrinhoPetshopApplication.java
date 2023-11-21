package com.example.carrinhopetshop;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "API Rest Petshop",
                version = "1.0.0",
                description = "Uma API REST para um petshop, que\n" +
                        "necessita de um sistema de carrinhos para a realização das vendas digitais.\n"
        )
)
public class CarrinhoPetshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrinhoPetshopApplication.class, args);
    }

}
