package com.cubetiqs.web.modules.file

import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.InputStream

object FileStorageFactory {
    private var provider: FileStorageProvider? = null

    fun setProvider(provider: FileStorageProvider) {
        FileStorageFactory.provider = provider
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
        return store(temp)
    }

    fun delete(fileName: String) {
        getProvider().delete(fileName)
    }

    fun deleteAll() {
        getProvider().deleteAll()
    }

    fun get(fileName: String): FileResponse {
        return getProvider().get(fileName)
    }

    fun zipAll(): ByteArray? {
        if (getProvider() is FileStorageZip) {
            return (getProvider() as FileStorageZip).zip(null)
        }

        return null
    }

    fun unzip(inputStream: InputStream): List<File> {
        if (getProvider() is FileStorageUnzip) {
            return (getProvider() as FileStorageUnzip).unzip(inputStream, null)
        }

        return emptyList()
    }
}