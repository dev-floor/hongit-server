package com.devfloor.hongit.api.auth.presentation

import com.devfloor.hongit.api.auth.application.AuthService
import com.devfloor.hongit.api.auth.application.request.AuthMailRequest
import com.devfloor.hongit.api.auth.presentation.AuthController.Companion.AUTH_API_URI
import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.core.common.config.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping(value = [AUTH_API_URI])
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun sendAuthenticationMail(@RequestBody request: AuthMailRequest) =
        authService.sendAuthenticationMail(request)

    @PutMapping(value = ["/tokens/{tokenId}"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun validateAuthToken(@PathVariable(value = "tokenId") tokenId: String) =
        authService.validateAuthToken(tokenId)

    companion object {
        const val AUTH_API_URI = "$BASE_API_URI/auth"
    }
}
