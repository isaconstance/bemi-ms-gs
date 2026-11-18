package com.github.isaconstance.ms.alerta.config;

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
                        .title("MS Alerta")
                        .version("1.0")
                        .description("Microsserviço de gerenciamento de alertas"))
                .servers(List.of(
                        new Server().url("/ms-alerta")
                ));
    }
}
