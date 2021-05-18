package com.cubetiqs.web.infrastructure.listener

import com.cubetiqs.web.infrastructure.event.BaseEvent

interface BaseEventListener {
    fun persist(event: BaseEvent) {
        return
    }
}