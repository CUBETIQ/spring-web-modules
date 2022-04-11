package com.cubetiqs.web.modules.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@RedisModule
@Configuration
class RedisConfig @Autowired constructor(
    private val connectionFactory: RedisConnectionFactory,
) {
    @Bean
    fun redisTemplate(): RedisTemplate<String, RedisKVModel> {
        val template = RedisTemplate<String, RedisKVModel>()
        template.setConnectionFactory(connectionFactory)
        return template
    }
}