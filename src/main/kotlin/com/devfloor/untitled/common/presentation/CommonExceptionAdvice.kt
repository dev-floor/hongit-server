package com.devfloor.untitled.common.presentation

import com.devfloor.untitled.common.exception.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
class CommonExceptionAdvice {
    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun handleNotFoundException(errorMessage: String): String = errorMessage

}
