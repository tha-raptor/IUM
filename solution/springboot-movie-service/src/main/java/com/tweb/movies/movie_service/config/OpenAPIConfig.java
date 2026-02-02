package com.tweb.movies.movie_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("mattia.nada@edu.unito.it");
        contact.setName("TWEB Nada-Beqiraj");
        contact.setUrl("https://github.com/tha-raptor/IUM/");

        // 3. Define General API Info
        Info info = new Info()
                .title("Movies Service API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage movies, actors, and metadata.")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));

        // 4. Return the Configuration
        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}