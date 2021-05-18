package com.cubetiqs.web.stereotype

import org.springframework.context.annotation.Lazy
import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

/**
 * @author sombochea <Sambo Chea>
 * @email sombochea@cubetiqs.com
 * @date 18/05/21
 * @since 1.0
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Lazy(value = true)
@Component
annotation class FunctionComponent(
    @get: AliasFor(annotation = Component::class)
    val value: String = ""
)