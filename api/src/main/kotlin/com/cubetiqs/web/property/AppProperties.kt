package com.cubetiqs.web.property

import com.cubetiqs.web.annotation.FunctionComponent
import org.springframework.beans.factory.annotation.Value

@FunctionComponent
class AppProperties(
    @Value("\${cubetiq.app.name:CUBETIQ Web API}")
    val appName: String,

    @Value("\${cubetiq.app.description:CUBETIQ Web APIs Documentation}")
    val appDescription: String,

    @Value("\${cubetiq.app.version:v0.0.1}")
    val appVersion: String,

    @Value("\${cubetiq.app.license-url:https://cubetiqs.com}")
    val appLicenseUrl: String,
)