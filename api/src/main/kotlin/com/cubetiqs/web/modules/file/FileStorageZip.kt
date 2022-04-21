package com.cubetiqs.web.modules.file

import java.io.ByteArrayOutputStream
import java.io.OutputStream

interface FileStorageZip {
    fun zip(sourceFolder: String?, os: OutputStream)
    fun zip(sourceFolder: String?): ByteArray {
        val os = ByteArrayOutputStream()
        zip(sourceFolder, os)
        return os.toByteArray()
    }
}