package com.cubetiqs.web.modules.uploader

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

@ConditionalOnProperty(name = ["module.uploader.enabled", "spring.datasource.enabled"], havingValue = "true")
annotation class UploaderModule