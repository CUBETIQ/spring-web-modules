package com.cubetiqs.web.controller

import com.cubetiqs.web.model.response.ApiInfoAuthorResponse
import com.cubetiqs.web.model.response.ApiInfoResponse
import com.cubetiqs.web.model.response.HealthResponse
import com.cubetiqs.web.util.RouteConstants
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Miscellaneous")
@RestController
@RequestMapping(RouteConstants.INDEX)
class IndexController @Autowired constructor(
    private val buildProperties: BuildProperties,
    private val environment: Environment,
) : BaseController {
    @GetMapping
    fun index(): ResponseEntity<ApiInfoResponse?> {
        val authors = listOf(
            ApiInfoAuthorResponse(name = "Sambo Chea", email = "sombochea@cubetiqs.com"),
            ApiInfoAuthorResponse(name = "CUBETIQ OSS", email = "oss@cubetiqs.com"),
        )
        val response = ApiInfoResponse(
            info = "API Operation is running normally on ${environment.activeProfiles.joinToString(separator = ",")}",
            name = buildProperties.name,
            service = buildProperties.artifact,
            version = buildProperties.version,
            date = buildProperties.time.toString(),
            commit = buildProperties["commitId"],
            authors = authors,
        )
        return response(response)
    }

    @GetMapping("/health")
    fun health(): ResponseEntity<HealthResponse?> {
        return response(
            HealthResponse.UP
        )
    }
}