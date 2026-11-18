package com.github.isaconstance.ms.idoso.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microsserviço de Idosos")
                        .description("API responsável pelo cadastro de idosos, contato de emergência e status de cuidado")
                        .version("v1"))

                .servers(List.of(
                        new Server().url("/ms-idoso")
                ));
    }
}
