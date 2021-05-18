package com.cubetiqs.web.controller

import com.cubetiqs.web.util.RouteConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@ApiIgnore
@RestController
@RequestMapping(RouteConstants.INDEX)
class IndexController @Autowired constructor(
    private val buildProperties: BuildProperties,
    private val environment: Environment,
) : BaseController {
    @GetMapping
    fun index(): ResponseEntity<Any?> {
        val view = mutableMapOf<String, Any?>()
        view["info"] = "API Operation is running normally on ${environment.activeProfiles.joinToString(separator = ",")}"
        view["name"] = buildProperties.name
        view["service"] = buildProperties.artifact
        view["version"] = buildProperties.version
        view["date"] = buildProperties.time
        view["commit"] = buildProperties["commitId"]

        return response(view)
    }
}