package com.cubetiqs.web.infrastructure.component

interface BaseFunctionComponent<I, O> {
    fun execute(input: I?): O?
}