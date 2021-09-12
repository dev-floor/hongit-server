package com.devfloor.hongit.api.auth.presentation

import com.devfloor.hongit.api.auth.application.AuthMailService
import com.devfloor.hongit.api.auth.application.request.AuthMailRequest
import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.core.common.config.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping(value = [BASE_API_URI])
class AuthController(
    private val authMailService: AuthMailService,
) {
    @PostMapping(value = ["/auth-mail"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun sendAuthenticationMail(@RequestBody request: AuthMailRequest) =
        authMailService.sendAuthenticationMail(request)
}
