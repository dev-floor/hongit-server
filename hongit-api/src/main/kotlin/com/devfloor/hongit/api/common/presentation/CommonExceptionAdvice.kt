package com.devfloor.hongit.api.common.presentation

import com.devfloor.hongit.api.common.application.response.ErrorResponse
import com.devfloor.hongit.api.common.exception.EntityNotFoundException
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

    private fun loggingError(func: String, res: ErrorResponse) =
        log.error("[${this::class.simpleName}.$func] ${res.errorClass} - response: $res")
}
