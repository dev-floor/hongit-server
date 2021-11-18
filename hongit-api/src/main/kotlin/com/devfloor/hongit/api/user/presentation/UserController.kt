package com.devfloor.hongit.api.user.presentation

import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.security.core.LoginUser
import com.devfloor.hongit.api.user.application.UserService
import com.devfloor.hongit.api.user.application.request.DestroyRequest
import com.devfloor.hongit.api.user.application.request.LoginRequest
import com.devfloor.hongit.api.user.application.request.PasswordModifyRequest
import com.devfloor.hongit.api.user.application.request.SignUpRequest
import com.devfloor.hongit.api.user.application.request.UserModifyRequest
import com.devfloor.hongit.api.user.application.response.ProfileResponse
import com.devfloor.hongit.api.user.application.response.TokenResponse
import com.devfloor.hongit.core.common.config.Slf4j
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import com.devfloor.hongit.core.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@Slf4j
@RestController
class UserController(
    private val userService: UserService,
) {
    @PostMapping(value = [SIGNUP_API_URI])
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Unit> {
        log.info("[UserController.signUp] 회원가입 - request: $request")
        return userService.signUp(request)
            .let {
                log.info("[UserController.signUp] 회원가입 완료")
                ResponseEntity.created(URI.create("$USER_API_URI/$it")).build()
            }
    }

    @PostMapping(value = [LOGIN_API_URI])
    @ResponseStatus(value = HttpStatus.OK)
    fun login(@RequestBody request: LoginRequest): TokenResponse {
        log.info("[UserController.showByNickname] 로그인 - request: $request")
        return userService.login(request)
            .also { log.info("[UserController.showByNickname] 로그인 완료 - response: $it") }
    }

    @GetMapping(value = [USER_API_URI])
    @ResponseStatus(value = HttpStatus.OK)
    fun showByNickname(@RequestParam nickname: String): ProfileResponse {
        log.info("[UserController.showByNickname] 닉네임 회원 조회 - nickname: $nickname")
        return userService.showByNickname(nickname)
            .also { log.info("[UserController.showByNickname] 닉네임 회원 조회 완료 - response: $it") }
    }

    @PutMapping(value = [ME_API])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun modifyUser(
        @RequestBody request: UserModifyRequest,
        @LoginUser loginUser: User,
    ) = userService.modifyUser(loginUser, request)

    @PatchMapping(value = [ME_API])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun modifyPassword(@RequestBody request: PasswordModifyRequest, @LoginUser loginUser: User) =
        userService.modifyPassword(request, loginUser.id)

    @DeleteMapping(value = [ME_API])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun destroy(@RequestBody request: DestroyRequest, @LoginUser loginUser: User) =
        userService.destroy(loginUser.id, request.password)

    companion object {
        const val SIGNUP_API_URI = "$BASE_API_URI/signup"
        const val USER_API_URI = "$BASE_API_URI/users"
        const val LOGIN_API_URI = "$BASE_API_URI/login"
        const val ME_API = "$BASE_API_URI/me"
    }
}
