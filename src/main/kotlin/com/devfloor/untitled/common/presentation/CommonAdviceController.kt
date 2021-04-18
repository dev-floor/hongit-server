package com.devfloor.untitled.common.presentation

import com.devfloor.untitled.common.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CommonAdviceController {
    @ExceptionHandler(value = [NotFoundException::class])
    fun handleNoSuchElementException(): ResponseEntity<Any> =
        ResponseEntity(HttpStatus.NOT_FOUND)
}
