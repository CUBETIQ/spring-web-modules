package com.cubetiqs.web

import com.cubetiqs.web.stereotype.FunctionComponent
import org.springframework.context.annotation.Lazy

@FunctionComponent
@Lazy(false)
class StaticContextInitializer {
    init {
        println("Static context is loaded...!")
    }
}