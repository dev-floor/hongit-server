package com.devfloor.hongit.api.user.presentation

import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.security.core.LoginUser
import com.devfloor.hongit.api.user.application.UserService
import com.devfloor.hongit.api.user.application.request.SignUpRequest
import com.devfloor.hongit.api.user.application.request.UserModifyRequest
import com.devfloor.hongit.api.user.application.response.ProfileResponse
import com.devfloor.hongit.core.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class UserController(
    private val userService: UserService,
) {
    @PostMapping(value = ["$BASE_API_URI/signup"])
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Unit> = userService.signUp(request)
        .let { ResponseEntity.created(URI.create("$USER_API_URI/$it")).build() }

    @GetMapping(value = [USER_API_URI])
    @ResponseStatus(value = HttpStatus.OK)
    fun showByNickname(@RequestParam nickname: String): ProfileResponse = userService.showByNickname(nickname)

    @PutMapping(value = ["$BASE_API_URI/me"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun modifyUser(
        @RequestBody request: UserModifyRequest,
        @LoginUser loginUser: User,
    ) = userService.modifyUser(loginUser, request)

    companion object {
        const val USER_API_URI = "$BASE_API_URI/users"
    }
}
