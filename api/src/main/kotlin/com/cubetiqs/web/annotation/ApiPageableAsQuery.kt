package com.cubetiqs.web.annotation

import org.springdoc.core.converters.models.PageableAsQueryParam

@Retention(AnnotationRetention.RUNTIME)
@PageableAsQueryParam
annotation class ApiPageableAsQuery()