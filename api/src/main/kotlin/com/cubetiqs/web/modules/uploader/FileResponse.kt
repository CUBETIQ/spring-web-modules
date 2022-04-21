package com.cubetiqs.web.modules.uploader

import java.io.File

open class FileResponse(
    open var file: File? = null,
    open var shortPath: String? = null,
)