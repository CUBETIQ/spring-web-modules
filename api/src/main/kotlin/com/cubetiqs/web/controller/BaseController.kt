package com.cubetiqs.web.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

interface BaseController {
    fun <T> response(
        data: T?,
        status: HttpStatus = HttpStatus.OK,
    ): ResponseEntity<T?> {
        return ResponseEntity.status(status).body(data)
    }
}