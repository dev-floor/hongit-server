package com.devfloor.untitled.comment.application

import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.user.application.ProfileResponse
import com.devfloor.untitled.user.domain.User

data class CommentResponse(
    val user: ProfileResponse,
    val anonymous: Boolean,
    val content: String,
    val favorites: Long,
) {
    constructor(
        user: User,
        comment: Comment,
        favorites: Long
    ) : this(
        user = ProfileResponse(user),
        anonymous = comment.anonymous,
        content = comment.content,
        favorites = favorites,
    )
}
