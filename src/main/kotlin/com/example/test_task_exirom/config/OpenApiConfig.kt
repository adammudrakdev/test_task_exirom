package com.example.test_task_exirom.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(Info().title("My API").version("1.0"))
            .servers(
                listOf(
                    Server().url("http://localhost:8080")
                )
            )
    }
}
