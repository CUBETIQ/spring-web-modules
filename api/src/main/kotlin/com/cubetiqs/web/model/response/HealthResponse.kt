package com.cubetiqs.web.model.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "HealthResponse", description = "Health Response")
data class HealthResponse(
    @Schema(name = "status", description = "Status for the service")
    val status: String,
) : BaseRequestModel {
    companion object {
        private const val STATUS_UP = "UP"
        private const val STATUS_DOWN = "DOWN"
        val UP get() = HealthResponse(STATUS_UP)
        val DOWN get() = HealthResponse(STATUS_DOWN)
    }
}
