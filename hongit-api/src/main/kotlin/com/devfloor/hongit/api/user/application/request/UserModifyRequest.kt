package com.devfloor.hongit.api.user.application.request

data class UserModifyRequest(
    val nickname: String,
    val userType: String,
    val image: String?,
    val github: String?,
    val blog: String?,
    val description: String?,
)
