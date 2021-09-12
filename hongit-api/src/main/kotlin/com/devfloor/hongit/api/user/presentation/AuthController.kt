package com.devfloor.hongit.api.user.presentation

import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.user.application.AuthService
import com.devfloor.hongit.api.user.presentation.AuthController.Companion.AUTH_API_URI
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [AUTH_API_URI])
class AuthController(
    private val authService: AuthService,
) {
    @GetMapping(value = ["/users"], params = ["username"])
    @ResponseStatus(HttpStatus.OK)
    fun validateUsername(@RequestParam username: String) = authService.validateUsername(username)

    @GetMapping(value = ["/users"], params = ["nickname"])
    @ResponseStatus(HttpStatus.OK)
    fun validateNickname(@RequestParam nickname: String) = authService.validateNickname(nickname)

    @GetMapping(value = ["/users"], params = ["classOf"])
    @ResponseStatus(HttpStatus.OK)
    fun validateClassOf(@RequestParam classOf: String) = authService.validateClassOf(classOf)

    companion object {
        const val AUTH_API_URI = "$BASE_API_URI/auth"
    }
}
