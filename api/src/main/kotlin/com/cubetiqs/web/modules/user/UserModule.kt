package com.cubetiqs.web.modules.user

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

@ConditionalOnProperty(name = ["modules.user.enabled", "spring.datasource.enabled"], havingValue = "true")
annotation class UserModule