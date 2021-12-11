package com.cubetiqs.web.controller.admin

import com.cubetiqs.web.controller.BaseController
import com.cubetiqs.web.util.RouteConstants
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Admin")
@RestController
@RequestMapping(RouteConstants.ADMIN)
class AdminController : BaseController {
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping
    fun getAdmin(): String {
        return "Admin"
    }
}