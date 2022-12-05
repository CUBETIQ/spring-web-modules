package com.cubetiqs.web.controller

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.view.RedirectView

@Hidden
@Controller
class ApiDoc {
    @GetMapping(value = [ "/api-doc", "/api-docs"])
    fun redirect(): RedirectView {
        return RedirectView("/swagger-ui/index.html")
    }
}