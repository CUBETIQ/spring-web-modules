package com.cubetiqs.web.modules.redis

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

@ConditionalOnProperty(name = ["spring.redis.enabled"], havingValue = "true")
annotation class RedisModule