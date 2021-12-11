package com.cubetiqs.web.annotation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement

@Retention(AnnotationRetention.RUNTIME)
@Operation(security = [SecurityRequirement(name = "bearerAuth")])
annotation class ApiBearerAuth()
