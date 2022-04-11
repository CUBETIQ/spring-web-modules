package com.cubetiqs.web.modules.user

import com.cubetiqs.web.util.RouteConstants
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
    private val userRepository: UserRepository,
) {
    @GetMapping
    @PageableAsQueryParam
    fun getAll(
        @Parameter(hidden = true)
        pageable: Pageable?,
    ): Page<UserEntity> {
        return userRepository.findAll(pageable ?: Pageable.unpaged())
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.CREATED)
    @PostMapping
    fun create(
        @RequestBody body: UserEntity
    ): UserEntity {
        return userRepository.save(body)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody body: UserEntity
    ): UserEntity {
        val user = userRepository.findById(UUID.fromString(id)).orElseThrow {
            throw IllegalArgumentException("User not found")
        }
        body.id = user.id
        return userRepository.save(body)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: String,
    ) {
        val user = userRepository.findById(UUID.fromString(id)).orElseThrow {
            throw IllegalArgumentException("User not found")
        }
        userRepository.delete(user)
    }
}