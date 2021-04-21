package com.devfloor.untitled.common.exception

data class EntityNotFoundException(override val message: String) : RuntimeException()
