package com.cubetiqs.web.modules.redis

import com.cubetiqs.web.util.RouteConstants
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.*

@RedisModule
@Tag(name = "Redis Controller")
@RestController
@RequestMapping(RouteConstants.INDEX + "redis")
class RedisController @Autowired constructor(
    private val redisTemplate: RedisTemplate<String, RedisKVModel>,
) {
    @GetMapping("/{key}")
    fun getAll(
        @PathVariable("key") key: String,
    ): Collection<RedisKVModel?> {
        return redisTemplate.opsForValue().multiGet(listOf(key)) ?: listOf()
    }

    @PostMapping
    fun set(
        @RequestBody body: RedisKVModel
    ): RedisKVModel {
        redisTemplate.opsForValue().set(body.key ?: throw IllegalArgumentException("Key is required"), body)
        return body
    }
}