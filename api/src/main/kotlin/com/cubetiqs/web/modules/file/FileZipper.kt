package com.cubetiqs.web.modules.file

import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object FileZipper {
    fun zip(sourceFolder: String, destFolder: String) {
        try {
            val zipFolder = if (destFolder.endsWith(".zip")) {
                ""
            } else {
                ".zip"
            }
            FileOutputStream(zipFolder).use { fos ->
                zipToStream(sourceFolder, fos)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun zipToStream(sourceFolder: String, os: OutputStream) {
        ZipOutputStream(os).use { zos ->
            val sourcePath: Path = Paths.get(sourceFolder)
            // Walk the tree structure using WalkFileTree method
            Files.walkFileTree(sourcePath, object : SimpleFileVisitor<Path>() {
                @Throws(IOException::class)
                override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
                    if (sourcePath != dir) {
                        zos.putNextEntry(ZipEntry(sourcePath.relativize(dir).toString() + "/"))
                        zos.closeEntry()
                    }
                    return FileVisitResult.CONTINUE
                }

                @Throws(IOException::class)
                override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
                    zos.putNextEntry(ZipEntry(sourcePath.relativize(file).toString()))
                    Files.copy(file, zos)
                    zos.closeEntry()
                    return FileVisitResult.CONTINUE
                }
            })
        }
    }

    fun zipToBytes(sourceFolder: String): ByteArray {
        val bos = ByteArrayOutputStream()
        zipToStream(sourceFolder, bos)
        return bos.toByteArray()
    }
}