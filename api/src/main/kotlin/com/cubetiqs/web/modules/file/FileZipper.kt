package com.cubetiqs.web.modules.file

import java.io.*
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream


object FileZipper {
    private const val BUFFER = 2048

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

    fun unzipFromFile(sourceZipFile: File, destFolder: String) {
        try {
            val zipFile = ZipFile(sourceZipFile)
            val entries = zipFile.entries()
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement()
                val destFile = File(destFolder, entry.name)
                if (entry.isDirectory) {
                    destFile.mkdirs()
                } else {
                    destFile.parentFile.mkdirs()
                    val istream = zipFile.getInputStream(entry)
                    val ostream = FileOutputStream(destFile)
                    val buffer = ByteArray(BUFFER)
                    var len: Int
                    while (istream.read(buffer).also { len = it } > 0) {
                        ostream.write(buffer, 0, len)
                    }
                    ostream.close()
                    istream.close()
                }
            }
            zipFile.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun unzip(fis: InputStream, destFolder: String) {
        try {
            val root = File(destFolder)
            if (!root.exists()) {
                root.mkdir()
            }
            val zis = ZipInputStream(BufferedInputStream(fis))
            var entry: ZipEntry?
            while (zis.nextEntry.also { entry = it } != null) {
                val fileName = entry?.name
                val file = File(destFolder + File.separator.toString() + fileName)
                if (entry?.isDirectory == false) {
                    extractFileContentFromArchive(file, zis)
                } else {
                    if (!file.exists()) {
                        file.mkdirs()
                    }
                }
                zis.closeEntry()
            }
            zis.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun extractFileContentFromArchive(file: File, zis: ZipInputStream) {
        val fos = FileOutputStream(file)
        val bos = BufferedOutputStream(fos, BUFFER)
        var len: Int
        val data = ByteArray(BUFFER)
        while (zis.read(data, 0, BUFFER).also { len = it } != -1) {
            bos.write(data, 0, len)
        }
        bos.flush()
        bos.close()
    }
}