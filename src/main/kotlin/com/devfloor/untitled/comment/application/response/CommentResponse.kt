package com.devfloor.untitled.comment.application.response

import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.user.application.ProfileResponse
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,

    val user: ProfileResponse,

    val anonymous: Boolean,

    val content: String,

    val favorites: Long,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val createdDate: LocalDateTime,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val modifiedDate: LocalDateTime,
) {
    constructor(
        comment: Comment,
        favorites: Long = 0,
    ) : this(
        id = comment.id,
        user = ProfileResponse(comment.author),
        anonymous = comment.anonymous,
        content = comment.content,
        favorites = favorites,
        createdDate = comment.createdDate,
        modifiedDate = comment.modifiedDate,
    )
}
