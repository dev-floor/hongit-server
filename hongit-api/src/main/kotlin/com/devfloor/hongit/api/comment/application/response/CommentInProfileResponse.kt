package com.devfloor.hongit.api.comment.application.response

import com.devfloor.hongit.core.comment.domain.Comment

data class CommentInProfileResponse(
    val comment: CommentResponse,

    val articleId: Long,

    val articleTitle: String?,
) {
    constructor(
        comment: Comment,
        favoriteCount: Long = 0,
    ) : this(
        comment = CommentResponse(comment, favoriteCount),
        articleId = comment.article.id,
        articleTitle = comment.article.title,
    )
}
