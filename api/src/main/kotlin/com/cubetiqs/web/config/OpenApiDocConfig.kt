package com.cubetiqs.web.config

import com.cubetiqs.web.property.AppProperties
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springdoc.core.GroupedOpenApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
)
class OpenApiDocConfig @Autowired constructor(
    val appProperties: AppProperties,
) {
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
                Info().title(appProperties.appName)
                    .description(appProperties.appDescription)
                    .version(appProperties.appVersion)
                    .license(License().name("Apache 2.0").url(appProperties.appLicenseUrl))
            )
    }
}