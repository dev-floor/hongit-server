package com.devfloor.hongit.api.user.application.request

data class ProfileModifyRequest(
    val nickname: String,
    val type: String,
    val image: String?,
    val github: String?,
    val blog: String?,
    val description: String?,
)
