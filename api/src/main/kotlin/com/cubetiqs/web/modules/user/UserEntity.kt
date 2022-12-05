package com.cubetiqs.web.modules.user

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator

@UserModule
@Entity
@Table(name = "`user`")
open class UserEntity(
    @Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(
        name = "custom-uuid",
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = [org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")]
    )
    @Column(columnDefinition = "BINARY(16)")
    open var id: UUID? = null,

    @Column(name = "`name`", length = 50)
    open var name: String? = null,

    @Column(name = "`username`", length = 50, unique = true)
    open var username: String? = null,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}