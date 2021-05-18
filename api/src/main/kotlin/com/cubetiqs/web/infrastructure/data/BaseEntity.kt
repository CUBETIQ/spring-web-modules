package com.cubetiqs.web.infrastructure.data

import java.io.Serializable

interface BaseEntity <ID : Serializable> : Serializable {
    fun setId(id: ID)
}