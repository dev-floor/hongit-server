package com.devfloor.hongit.api.user.presentation

import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.user.application.AuthService
import com.devfloor.hongit.api.user.application.request.JoinRequest
import com.devfloor.hongit.api.user.presentation.UserController.Companion.USER_API_URI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping(value = [BASE_API_URI])
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping(value = ["/join"])
    fun join(@RequestBody request: JoinRequest): ResponseEntity<Unit> {
        val userId = authService.join(request)
        return ResponseEntity.created(URI.create("$USER_API_URI/$userId")).build()
    }
}
