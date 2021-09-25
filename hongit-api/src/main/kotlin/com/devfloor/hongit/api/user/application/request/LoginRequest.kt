package com.devfloor.hongit.api.user.application.request

import com.devfloor.hongit.api.common.utils.ApiUtils

data class LoginRequest(
    val username: String,
    val password: String,
) {
    override fun toString(): String = "LoginRequest(username=$username, password=${ApiUtils.mask(password)})"
}
