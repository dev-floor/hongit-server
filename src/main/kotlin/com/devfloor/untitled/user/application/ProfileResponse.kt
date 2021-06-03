package com.devfloor.untitled.user.application

import com.devfloor.untitled.common.application.response.BaseEnumResponse
import com.devfloor.untitled.user.domain.User

data class ProfileResponse(
    val nickname: String,
    val type: BaseEnumResponse,
    val image: String?,
    val github: String?,
    val blog: String?,
    val description: String?,
) {
    constructor(user: User) : this(
        nickname = user.nickname,
        type = BaseEnumResponse(user.type),
        image = user.image,
        github = user.github,
        blog = user.blog,
        description = user.description,
    )
}
