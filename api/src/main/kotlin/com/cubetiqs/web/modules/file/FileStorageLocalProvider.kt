package com.cubetiqs.web.modules.file

import java.io.File
import java.io.FileNotFoundException
import java.io.OutputStream

open class FileStorageLocalProvider(
    private val basePath: String,
) : FileStorageProvider, FileStorageZipper {
    private fun loadBasePath(fileName: String): String {
        val prefixPath = if (basePath.endsWith("/")) {
            ""
        } else {
            "/"
        }

        return basePath + prefixPath + fileName
    }

    override fun store(file: File): FileResponse {
        if (!file.exists()) {
            throw FileNotFoundException("File not found")
        }

        val path = loadBasePath(file.name)
        val savedFile = file.copyTo(File(path), true)
        return FileResponse(
            file = savedFile,
            shortPath = path,
        )
    }

    override fun get(fileName: String): FileResponse {
        val path = loadBasePath(fileName)
        val file = File(path)
        if (!file.exists()) {
            throw FileNotFoundException("File $fileName not found")
        }

        return FileResponse(
            file = file,
            shortPath = path,
        )
    }

    override fun delete(fileName: String) {
        val path = loadBasePath(fileName)
        val file = File(path)
        if (file.isFile) {
            file.delete()
        } else {
            throw IllegalArgumentException("File $fileName not found")
        }
    }

    override fun zip(sourceFolder: String?, os: OutputStream) {
        FileZipper.zipToStream(sourceFolder ?: basePath, os)
    }

    override fun zip(sourceFolder: String?): ByteArray {
        return FileZipper.zipToBytes(sourceFolder ?: basePath)
    }
}