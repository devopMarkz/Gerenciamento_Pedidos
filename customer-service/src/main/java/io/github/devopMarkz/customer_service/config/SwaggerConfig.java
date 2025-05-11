package io.github.devopMarkz.customer_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Clientes.",
                description = "API responsável por gerenciar os clientes.",
                version = "v1",
                contact = @Contact(
                        name = "Marcos André",
                        email = "marcosdev2002@gmail.com"
                )
        )
)
public class SwaggerConfig {
}
