package com.cubetiqs.web.modules.uploader

import com.cubetiqs.web.modules.file.FileStorageFactory
import com.cubetiqs.web.util.RouteConstants
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.converters.models.PageableAsQueryParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*
import jakarta.servlet.http.HttpServletResponse

@UploaderModule
@Tag(name = "Uploader Controller")
@RestController
@RequestMapping(RouteConstants.INDEX + "uploader")
class UploaderController @Autowired constructor(
    private val repository: UploaderRepository,
) {
    @GetMapping
    @PageableAsQueryParam
    @Operation(summary = "Get all files")
    fun getAll(
        @Parameter(hidden = true)
        pageable: Pageable?,
    ): Page<UploaderEntity> {
        return repository.findAll(pageable ?: Pageable.unpaged())
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @GetMapping("/{id}")
    @Operation(summary = "Get a file by id")
    fun get(
        @PathVariable id: String,
    ): UploaderEntity {
        val entity = repository.findById(UUID.fromString(id)).orElseThrow {
            throw IllegalArgumentException("File not found")
        }
        return repository.save(entity)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @GetMapping("/{id}/stream", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    @Operation(summary = "Get file stream by id")
    fun stream(
        @PathVariable id: String,
        @RequestParam(required = false, value = "download") download: Boolean?,
        response: HttpServletResponse,
    ) {
        val entity = repository.findById(UUID.fromString(id)).orElseThrow {
            throw IllegalArgumentException("User not found")
        }

        if (!entity.isFileExists()) {
            throw IllegalArgumentException("File not found")
        }

        val file = entity.getFile() ?: throw IllegalArgumentException("File source not found")
        val disposition = if (download == true) {
            "attachment"
        } else {
            "inline"
        }

        response.setHeader("Content-Disposition", "$disposition; filename=\"${entity.filename}\"")
        response.contentType = entity.contentType
        response.setContentLengthLong(file.length())

        FileCopyUtils.copy(file.readBytes(), response.outputStream)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @GetMapping("/zip", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    @Operation(summary = "Zip all files")
    fun zipAll(
        @RequestParam(required = false, value = "filename", defaultValue = "files") filename: String?,
        response: HttpServletResponse,
    ) {
        val zipBytes = FileStorageFactory.zipAll() ?: throw IllegalArgumentException("Zip file not found")
        response.setHeader("Content-Disposition", "attachment; filename=\"$filename.zip\"")
        response.contentType = "application/zip"
        response.setContentLengthLong(zipBytes.size.toLong())

        FileCopyUtils.copy(zipBytes, response.outputStream)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.CREATED)
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "Upload a file")
    fun create(
        @RequestPart file: MultipartFile,
    ): UploaderEntity {
        val entity = UploaderEntity.fromFile(file)
        return repository.save(entity)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.CREATED)
    @PostMapping("/unzip", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "Unzip a file")
    fun unzip(
        @RequestPart file: MultipartFile,
    ): List<UploaderEntity> {
        val files = FileStorageFactory.unzip(file.inputStream)
            .map { UploaderEntity.fromFileWithoutStore(it) }
        return repository.saveAll(files)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(summary = "Update a file by id")
    fun update(
        @PathVariable id: String,
        @RequestBody body: UploaderEntity
    ): UploaderEntity {
        val entity = repository.findById(UUID.fromString(id)).orElseThrow {
            throw IllegalArgumentException("File not found")
        }
        body.id = entity.id
        return repository.save(body)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a file by id")
    fun delete(
        @PathVariable id: String,
    ) {
        val entity = repository.findById(UUID.fromString(id)).orElseThrow {
            throw IllegalArgumentException("File not found")
        }
        if (!entity.path.isNullOrEmpty()) {
            FileStorageFactory.delete(entity.path!!)
        }
        repository.delete(entity)
    }

    @ResponseStatus(value = org.springframework.http.HttpStatus.NO_CONTENT)
    @DeleteMapping
    @Operation(summary = "Delete all files")
    fun deleteAll() {
        FileStorageFactory.deleteAll()
        repository.deleteAll()
    }
}