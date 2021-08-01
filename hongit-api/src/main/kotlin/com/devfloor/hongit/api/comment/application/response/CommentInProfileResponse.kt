package com.devfloor.hongit.api.comment.application.response

import com.devfloor.hongit.api.article.presentation.ArticleController.Companion.ARTICLE_API_URI
import com.devfloor.hongit.api.user.application.response.ProfileResponse
import com.devfloor.hongit.core.comment.domain.Comment
import com.fasterxml.jackson.annotation.JsonFormat
import java.net.URI
import java.time.LocalDateTime

data class CommentInProfileResponse(
    val id: Long,

    val author: ProfileResponse,

    val content: String,

    val favoriteCount: Long,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val createdDate: LocalDateTime,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    val modifiedDate: LocalDateTime,

    val articleUri: URI,

    val articleTitle: String?,
) {
    constructor(
        comment: Comment,
        favoriteCount: Long = 0,
    ) : this(
        id = comment.id,
        author = ProfileResponse(comment.author),
        content = comment.content,
        favoriteCount = favoriteCount,
        createdDate = comment.createdAt,
        modifiedDate = comment.modifiedAt,
        articleUri = URI.create("$ARTICLE_API_URI/${comment.article.id}"),
        articleTitle = comment.article.title
    )
}
