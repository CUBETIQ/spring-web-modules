package com.cubetiqs.web.modules.uploader

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@UploaderModule
@Repository
interface UploaderRepository : JpaRepository<UploaderEntity, UUID>