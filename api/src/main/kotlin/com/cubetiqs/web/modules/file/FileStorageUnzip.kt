package com.cubetiqs.web.modules.file

import java.io.File
import java.io.InputStream

interface FileStorageUnzip {
    fun unzip(inputStream: InputStream, destinationFolder: String?): List<File>
}