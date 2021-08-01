package com.devfloor.hongit.api.user.presentation

import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.user.application.UserService
import com.devfloor.hongit.api.user.application.response.ProfileResponse
import com.devfloor.hongit.api.user.presentation.UserController.Companion.USER_API_URI
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [USER_API_URI])
class UserController(
    private val userService: UserService,
) {
    @GetMapping(value = ["/{userId}"])
    @ResponseStatus(value = HttpStatus.OK)
    fun showByUserId(@PathVariable userId: Long): ProfileResponse =
        userService.showByUserId(userId)

    companion object {
        const val USER_API_URI = "$BASE_API_URI/users"
    }
}
