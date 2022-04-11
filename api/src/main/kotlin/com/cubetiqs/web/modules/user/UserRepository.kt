package com.cubetiqs.web.modules.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@UserModule
@Repository
interface UserRepository : JpaRepository<UserEntity, UUID>