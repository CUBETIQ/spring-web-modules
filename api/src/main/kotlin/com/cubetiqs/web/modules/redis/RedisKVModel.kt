package com.cubetiqs.web.modules.redis

import java.io.Serializable

data class RedisKVModel(
    var key: String? = null,
    var value: Any? = null,
) : Serializable {
    companion object {
        fun create(key: String, value: Any): RedisKVModel {
            return RedisKVModel(key, value)
        }
    }
}