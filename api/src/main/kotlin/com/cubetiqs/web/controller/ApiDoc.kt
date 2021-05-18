package com.cubetiqs.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.view.RedirectView
import springfox.documentation.annotations.ApiIgnore

@ApiIgnore
@Controller
class ApiDoc {
    @GetMapping(value = [ "/api-doc", "/api-docs"])
    fun redirect(): RedirectView {
        return RedirectView("/swagger-ui/")
    }
}