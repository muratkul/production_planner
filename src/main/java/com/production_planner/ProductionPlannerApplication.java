package com.production_planner;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Production Planner Application Documentation",
                description = "This application used to plan the production quantities with self made models to " +
                        "simulate a real production and calculate the needed components.",
                version = "v1",
                contact = @Contact(
                        name = "Murat Kul",
                        email = "murat.kul@outlook.com"
                ),
                license = @License(
                        name = "Apache 2.0"
                )
        )
)
public class ProductionPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductionPlannerApplication.class, args);
    }

}
