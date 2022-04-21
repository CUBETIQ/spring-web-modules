package com.cubetiqs.web.modules.file

import java.io.File

interface FileStorageProvider {
    fun store(file: File): FileResponse
    fun get(fileName: String): FileResponse
    fun delete(fileName: String)
}