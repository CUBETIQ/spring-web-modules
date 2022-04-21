package com.cubetiqs.web.modules.user

import com.cubetiqs.web.util.RouteConstants
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.converters.models.PageableAsQueryParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*

@UserModule
@Tag(name = "User Controller")
@RestController
@RequestMapping(RouteConstants.INDEX + "user")
class UserController @Autowired constructor(
    private val repository: UserRepository,
) {
    @GetMapping
    @PageableAsQueryParam
    @Operation(summary = "Get all users")
    fun getAll(
        @Parameter(hidden = true)
        pageable: Pageable?,
    ): Page<UserEntity> {
        return repository.findAll(pageable ?: Pageable.unpaged())
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id")
    fun get(
        @PathVariable id: String,
    ): UserEntity {
        val entity = repository.findById(UUID.fromString(id)).orElseThrow {
            throw IllegalArgumentException("User not found")
        }
        return repository.save(entity)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create a user")
    fun create(
        @RequestBody body: UserEntity
    ): UserEntity {
        return repository.save(body)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(summary = "Update a user by id")
    fun update(
        @PathVariable id: String,
        @RequestBody body: UserEntity
    ): UserEntity {
        val entity = repository.findById(UUID.fromString(id)).orElseThrow {
            throw IllegalArgumentException("User not found")
        }
        body.id = entity.id
        return repository.save(body)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    fun delete(
        @PathVariable id: String,
    ) {
        val entity = repository.findById(UUID.fromString(id)).orElseThrow {
            throw IllegalArgumentException("User not found")
        }
        repository.delete(entity)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.NO_CONTENT)
    @DeleteMapping
    @Operation(summary = "Delete all users")
    fun deleteAll() {
        repository.deleteAll()
    }
}