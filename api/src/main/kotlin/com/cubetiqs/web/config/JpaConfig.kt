package com.cubetiqs.web.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@ConditionalOnProperty(prefix = "spring.datasource", name = ["enabled"], havingValue = "true")
@Configuration
@EnableJpaAuditing
class JpaConfig