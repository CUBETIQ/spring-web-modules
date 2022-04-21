package com.cubetiqs.web.modules.uploader

import java.io.File

interface FileStorageProvider {
    fun store(file: File): FileResponse
    fun get(fileName: String): FileResponse
    fun delete(fileName: String)
}