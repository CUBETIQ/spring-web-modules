package com.cubetiqs.web.annotation

import io.swagger.v3.oas.annotations.Parameter

@Retention(AnnotationRetention.RUNTIME)
@Parameter(hidden = true)
annotation class ApiParamHidden()