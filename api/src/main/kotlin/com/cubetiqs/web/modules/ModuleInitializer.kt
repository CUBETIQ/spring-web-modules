package com.cubetiqs.web.modules

import com.cubetiqs.web.modules.uploader.FileStorageFactory
import com.cubetiqs.web.modules.uploader.FileStorageLocalProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
@Lazy(false)
class ModuleInitializer constructor(
    @Value("\${module.uploader.local.path:./uploads}")
    private val fileBasePath: String,
) {
    init {
        FileStorageFactory.setProvider(
            FileStorageLocalProvider(fileBasePath)
        )
    }
}