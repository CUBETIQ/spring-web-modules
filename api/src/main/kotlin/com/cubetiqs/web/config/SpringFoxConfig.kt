package com.cubetiqs.web.config

import com.cubetiqs.web.property.AppProperties
import com.cubetiqs.web.util.AppConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SpringFoxConfig @Autowired constructor(
    val buildProperties: BuildProperties,
    val appProperties: AppProperties,
) {
    companion object {
        private const val AUTHORIZATION_KEY: String = "Bearer"
        private const val AUTHORIZATION_HEADER: String = "Authorization"
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference(AUTHORIZATION_KEY, authorizationScopes))
    }

    private fun securityContext(): SecurityContext? {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build()
    }

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(AppConstants.BASE_PACKAGE_API_DOCS))
            .paths(PathSelectors.any())
            .build()
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
            .apiInfo(apiInfo())

            // Allow to configurable custom pagination of request in swagger ui //
            // .directModelSubstitute(Pageable::class.java, SwaggerPageable::class.java)
            // .directModelSubstitute(UrlParamable::class.java, UrlParamableSwaggerView::class.java)
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .description(appProperties.appDescription)
            .title(appProperties.appName)
            .version(buildProperties.version)
            .contact(
                Contact(
                    "CUBETIQ Solution",
                    "https://cubetiqs.com",
                    "ops@cubetiqs.com",
                )
            )
            .build()
    }

    private fun apiKey(): ApiKey {
        return ApiKey(
            AUTHORIZATION_KEY,
            AUTHORIZATION_HEADER,
            "header",
        )
    }
}
