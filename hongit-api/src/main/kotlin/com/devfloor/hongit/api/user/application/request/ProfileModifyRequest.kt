package com.devfloor.hongit.api.user.application.request

data class ProfileModifyRequest(
    val image: String?,
    val github: String?,
    val blog: String?,
    val description: String?,
)
