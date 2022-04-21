package com.cubetiqs.web.modules.uploader

import org.springframework.web.multipart.MultipartFile
import java.io.File

object FileStorageFactory {
    private var provider: FileStorageProvider? = null

    fun setProvider(provider: FileStorageProvider) {
        this.provider = provider
    }

    fun getProvider(): FileStorageProvider {
        return provider ?: throw IllegalStateException("FileStorageProvider is not set")
    }

    fun store(file: File): FileResponse {
        return getProvider().store(file)
    }

    fun store(file: MultipartFile): FileResponse {
        val tempPath = System.getProperty("java.io.tmpdir") ?: if (System.getProperty("os.name").lowercase()
                .contains("win")
        ) "C:\\Windows\\Temp" else "/tmp"
        val temp = File("$tempPath/${file.originalFilename}")
        file.transferTo(temp)
        return this.store(temp)
    }

    fun delete(fileName: String) {
        getProvider().delete(fileName)
    }

    fun get(fileName: String): FileResponse {
        return getProvider().get(fileName)
    }
}