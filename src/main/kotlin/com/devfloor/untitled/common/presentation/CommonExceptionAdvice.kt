package com.devfloor.untitled.common.presentation

import com.devfloor.untitled.common.exception.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * common 예외에 대한 처리를 하는 controller
 */
@ControllerAdvice
class CommonExceptionAdvice {
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun handleEntityNotFoundException(e: EntityNotFoundException): String = e.message
}
