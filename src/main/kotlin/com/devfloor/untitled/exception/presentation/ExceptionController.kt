package com.devfloor.untitled.exception.presentation

import com.devfloor.untitled.exception.domain.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionController {
    @ExceptionHandler(value = [NotFoundException::class])
    fun handleNoSuchElementException(): ResponseEntity<Any> =
        ResponseEntity(HttpStatus.NOT_FOUND)
}