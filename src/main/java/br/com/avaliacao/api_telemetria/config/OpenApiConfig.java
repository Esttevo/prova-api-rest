package br.com.avaliacao.api_telemetria.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Api de gerenciamento de motores")
                        .description("Guarda e exibe informações sobre os motores e seus estados")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Suporte Técnico")
                                .email("mateus_mathias@estudante.sesisenai.org.br")
                        )
                );
    }
}
