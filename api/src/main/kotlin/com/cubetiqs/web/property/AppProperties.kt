package com.cubetiqs.web.property

import com.cubetiqs.web.stereotype.FunctionComponent
import org.springframework.beans.factory.annotation.Value

@FunctionComponent
class AppProperties (
    @Value("\${cubetiq.app.name:CUBETIQ API Service}")
    val appName: String,

    @Value("\${cubetiq.app.description:CUBETIQ APIs Documentation}")
    val appDescription: String,
)