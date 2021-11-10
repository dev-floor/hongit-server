package com.devfloor.hongit.api.user.application.request

import org.springframework.web.multipart.MultipartFile

data class UserModifyRequest(
    val nickname: String,
    val userType: String,
    val image: MultipartFile?,
    val github: String?,
    val blog: String?,
    val description: String?,
)
