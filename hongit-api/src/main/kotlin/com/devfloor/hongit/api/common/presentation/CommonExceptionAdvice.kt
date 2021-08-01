package com.devfloor.hongit.api.common.presentation

import com.devfloor.hongit.api.common.application.response.ErrorResponse
import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.api.common.exception.ErrorMessages.Common.ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * common 예외에 대한 처리를 하는 controller
 */
@Slf4j
@RestControllerAdvice
class CommonExceptionAdvice {
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun handleEntityNotFoundException(e: EntityNotFoundException): ErrorResponse =
        ErrorResponse(e.javaClass.simpleName, e.message)
            .also { loggingError(::handleEntityNotFoundException.name, it) }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleIllegalArgumentException(e: IllegalArgumentException): ErrorResponse =
        ErrorResponse(e.javaClass.simpleName, e.message ?: ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE)
            .also { loggingError(::handleIllegalArgumentException.name, it) }

    private fun loggingError(func: String, res: ErrorResponse) =
        log.error("[${this::class.simpleName}.$func] ${res.errorClass} - response: $res")
}
