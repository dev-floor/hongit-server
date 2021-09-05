package com.devfloor.hongit.api.comment.application.response

import com.devfloor.hongit.core.comment.domain.Comment
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,

    val authorName: String,

    val anonymous: Boolean,

    val content: String,

    val favoriteCount: Long,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val createdDate: LocalDateTime,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val modifiedDate: LocalDateTime,
) {
    constructor(
        comment: Comment,
        favoriteCount: Long = 0,
    ) : this(
        id = comment.id,
        authorName = comment.author.nickname,
        anonymous = comment.anonymous,
        content = comment.content,
        favoriteCount = favoriteCount,
        createdDate = comment.createdAt,
        modifiedDate = comment.modifiedAt,
    )
}
