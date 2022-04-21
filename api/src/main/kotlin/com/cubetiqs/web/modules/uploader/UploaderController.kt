package com.cubetiqs.web.modules.uploader

import com.cubetiqs.web.util.RouteConstants
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.converters.models.PageableAsQueryParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*

@UploaderModule
@Tag(name = "Uploader Controller")
@RestController
@RequestMapping(RouteConstants.INDEX + "uploader")
class UploaderController @Autowired constructor(
    private val repository: UploaderRepository,
) {
    @GetMapping
    @PageableAsQueryParam
    fun getAll(
        @Parameter(hidden = true)
        pageable: Pageable?,
    ): Page<UploaderEntity> {
        return repository.findAll(pageable ?: Pageable.unpaged())
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.CREATED)
    @PostMapping
    fun create(
        @RequestBody body: UploaderEntity
    ): UploaderEntity {
        return repository.save(body)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody body: UploaderEntity
    ): UploaderEntity {
        val user = repository.findById(UUID.fromString(id)).orElseThrow {
            throw IllegalArgumentException("File not found")
        }
        body.id = user.id
        return repository.save(body)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: String,
    ) {
        val user = repository.findById(UUID.fromString(id)).orElseThrow {
            throw IllegalArgumentException("File not found")
        }
        repository.delete(user)
    }
}