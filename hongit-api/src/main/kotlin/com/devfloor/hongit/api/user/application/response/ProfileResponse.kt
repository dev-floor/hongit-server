package com.devfloor.hongit.api.user.application.response

import com.devfloor.hongit.api.common.application.response.BaseEnumResponse
import com.devfloor.hongit.core.user.domain.User

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
