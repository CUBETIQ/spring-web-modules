package com.cubetiqs.web.model.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "ApiInfoResponse", description = "ApiInfoResponse")
data class ApiInfoResponse(
    val name: String,
    val info: String,
    val service: String,
    val version: String,
    val date: String,
    val commit: String,
    val authors: Collection<ApiInfoAuthorResponse> = listOf(),
) : BaseRequestModel

@Schema(name = "ApiInfoAuthorResponse", description = "ApiInfoAuthorResponse")
data class ApiInfoAuthorResponse(
    val name: String,
    val email: String,
) : BaseRequestModel