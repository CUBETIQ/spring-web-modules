package com.cubetiqs.web.modules.uploader

import com.cubetiqs.web.modules.file.FileStorageFactory
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.InputStream
import java.io.Serializable
import java.nio.file.Files
import java.util.*
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator

@UploaderModule
@Entity
@Table(name = "`uploader`")
@EntityListeners(AuditingEntityListener::class)
open class UploaderEntity(
    @Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(
        name = "custom-uuid",
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = [org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")]
    )
    @Column(columnDefinition = "BINARY(16)")
    open var id: UUID? = null,

    @Column(name = "`filename`")
    open var filename: String? = null,

    @Column(name = "`content_type`")
    open var contentType: String? = null,

    @Column(name = "`content_length`")
    open var contentLength: Long? = null,

    @Column(name = "`path`", length = 300)
    open var path: String? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "`created_at`")
    @CreatedDate
    open var createdAt: Date? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "`updated_at`")
    @LastModifiedDate
    open var updatedAt: Date? = null,

    @Column(length = 30, name = "`provider_type`")
    open var providerType: String? = null,
) : Serializable {
    @Transient
    @JsonIgnore
    private var partFile: MultipartFile? = null

    @Transient
    @JsonIgnore
    private var file: File? = null

    @Transient
    fun isFileExists(): Boolean {
        val temp = getFile()
        return temp?.exists() ?: false
    }

    @Transient
    @JsonIgnore
    fun getFile(): File? {
        if (file == null) {
            file = File(path ?: return null)
        }
        return file
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UploaderEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    companion object {
        fun fromFile(file: MultipartFile): UploaderEntity {
            // transfer to file storage first
            val store = FileStorageFactory.store(file)
            val uploader = UploaderEntity()
            uploader.partFile = file
            uploader.providerType = "local"
            uploader.filename = file.originalFilename
            uploader.contentType = file.contentType
            uploader.contentLength = file.size
            uploader.path = store.shortPath
            return uploader
        }

        fun fromFileWithoutStore(file: File): UploaderEntity {
            val uploader = UploaderEntity()
            uploader.file = file
            uploader.providerType = "local"
            uploader.filename = file.name
            uploader.contentType = Files.probeContentType(file.toPath())
            uploader.contentLength = file.length()
            uploader.path = file.path
            return uploader
        }

        fun fromUploader(uploader: UploaderEntity): MultipartFile? {
            if (uploader.partFile != null) {
                return uploader.partFile
            }

            val file = try {
                File(uploader.path!!)
            } catch (ex: Exception) {
                null
            } ?: return null

            return object : MultipartFile {
                override fun getInputStream(): InputStream {
                    return file.inputStream()
                }

                override fun getName(): String {
                    return file.name
                }

                override fun getOriginalFilename(): String? {
                    return uploader.filename
                }

                override fun getContentType(): String? {
                    return uploader.contentType
                }

                override fun isEmpty(): Boolean {
                    return file.length() == 0L
                }

                override fun getSize(): Long {
                    return file.length()
                }

                override fun getBytes(): ByteArray {
                    return file.readBytes()
                }

                override fun transferTo(dest: File) {
                    file.copyTo(dest)
                }
            }
        }
    }
}