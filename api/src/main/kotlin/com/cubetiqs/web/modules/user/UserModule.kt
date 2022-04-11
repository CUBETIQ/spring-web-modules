package com.cubetiqs.web.modules.user

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

@ConditionalOnProperty(name = ["module.user.enabled", "spring.datasource.enabled"], havingValue = "true")
annotation class UserModule