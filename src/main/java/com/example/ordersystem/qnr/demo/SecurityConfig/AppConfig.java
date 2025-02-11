package com.example.ordersystem.qnr.demo.SecurityConfig;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }


    @Bean
    public OpenAPI openAPI() {
        OpenAPI info = new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("qnr - Panos Foteinopoulos - 2025 REST API")
                        .description("This API is used in Panagiotis Foteinopoulos Assessment")
                        .version("1.0").contact(new Contact().name("Panagiotis Foteinopoulos")
                                .email("it2021154@hua.gr").url("https://github.com/Panos994"))
                        .license(new License().name("License of API")
                                .url("https://swagger.io/license/")));
        return info;
    }


    //here it provides a BcryptPasswordEncoder bean in order to  hash passwords securely.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


/*Sum up of my security:
* I use Role based access control
* I am securing password with hashing
*I am using blacklist for token invalidation
*I have proper CORS and CSRF configurations
*/




