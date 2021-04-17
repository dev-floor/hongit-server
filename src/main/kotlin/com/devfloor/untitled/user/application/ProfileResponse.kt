package com.devfloor.untitled.user.application

import com.devfloor.untitled.user.domain.User
import com.devfloor.untitled.user.domain.UserType


data class ProfileResponse(
    val nickname: String,
    val type: UserType,
    val image: String,
    val github: String?,
    val blog: String?,
    val description: String?,
) {
    constructor(user: User) : this(
        nickname = user.nickname,
        type = user.type,
        image = user.image,
        github = user.github,
        blog = user.blog,
        description = user.description,
    )
}
