package com.cubetiqs.web.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",

)
class OpenApiDocConfig {
    companion object {
        private val ADMIN_API_PATH get() = "/admin/**"
        private val DEFAULT_API_PATH get() = "/**"
    }

    @Bean
    fun defaultApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("default")
            .pathsToMatch(DEFAULT_API_PATH)
            .pathsToExclude("/error", ADMIN_API_PATH)
            .packagesToScan("com.cubetiqs.web")
            .build()
    }

    @Bean
    fun adminApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("admin")
            .pathsToMatch(ADMIN_API_PATH)
            .build()
    }

    @Bean
    fun cubetiqOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("CUBETIQ Web API")
                    .description("CUBETIQ Spring Web API Application")
                    .version("v0.0.1")
                    .license(License().name("Apache 2.0").url("https://cubetiqs.com"))
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("CUBETIQ Web Wiki Documentation")
                    .url("https://cubetiqs.com")
            )
    }
}