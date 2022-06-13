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
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

//import org.springdoc.core.SpringDocUtils
//import org.springdoc.core.converters.models.Pageable
//import javax.servlet.http.HttpServletRequest

@ConditionalOnProperty(name = ["springdoc.api-docs.enabled"], havingValue = "true")
@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
)
@OpenAPIDefinition(
    servers = [
        Server(url = "/")
    ],
)
class OpenApiDocConfig @Autowired constructor(
    val appProperties: AppProperties,
) {
    companion object {
        private val DEFAULT_API_PATH get() = "/**"
    }

//    init {
//        SpringDocUtils.getConfig()
//            .replaceParameterObjectWithClass(org.springframework.data.domain.Pageable::class.java, Pageable::class.java)
//            .replaceParameterObjectWithClass(
//                org.springframework.data.domain.PageRequest::class.java,
//                Pageable::class.java
//            )
//            .removeRequestWrapperToIgnore(
//                HttpServletRequest::class.java,
//                org.springframework.data.domain.Pageable::class.java,
//                org.springframework.data.domain.PageRequest::class.java,
//            )
//    }

    @Bean
    fun defaultApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("default")
            .pathsToMatch(DEFAULT_API_PATH)
            .packagesToScan("com.cubetiqs.web")
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