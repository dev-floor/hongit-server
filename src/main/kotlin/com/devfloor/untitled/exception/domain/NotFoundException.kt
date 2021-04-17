package com.devfloor.untitled.exception.domain

data class NotFoundException(
    override val message: String?
) : Exception() {
}