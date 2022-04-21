package com.cubetiqs.web.modules.uploader

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.*

@UploaderModule
@Entity
@Table(name = "uploader")
open class UploaderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: UUID? = null,

    @Column(name = "filename")
    open var filename: String? = null,

    @Column(name = "content_type")
    open var contentType: String? = null,

    @Column(name = "content_length")
    open var contentLength: Long? = null,

    @Column(name = "path", length = 300)
    open var path: String? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    open var createdAt: Date? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    open var updatedAt: Date? = null,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UploaderEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}