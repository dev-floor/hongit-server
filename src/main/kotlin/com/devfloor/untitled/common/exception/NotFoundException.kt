package com.devfloor.untitled.common.exception

data class NotFoundException(
    override val message: String?
) : RuntimeException() {
}
