package com.devfloor.hongit.api.user.application.response

import com.devfloor.hongit.api.security.web.AuthorizationType

data class TokenResponse(
    val token: String,
    val type: AuthorizationType,
)
