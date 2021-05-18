package com.cubetiqs.web.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

interface BaseController {
    fun response(
        data: Any?,
        status: HttpStatus = HttpStatus.OK,
    ): ResponseEntity<Any?> {
        return ResponseEntity.status(status).body(data)
    }
}