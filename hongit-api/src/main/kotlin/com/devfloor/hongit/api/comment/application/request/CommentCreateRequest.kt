package com.devfloor.hongit.api.comment.application.request

import com.devfloor.hongit.core.article.domain.Article
import com.devfloor.hongit.core.comment.domain.Comment
import com.devfloor.hongit.core.user.domain.User

data class CommentCreateRequest(
    val articleId: Long,
    val anonymous: Boolean,
    val content: String,
) {
    fun toComment(article: Article, author: User) = Comment(
        article = article,
        author = author,
        anonymous = this.anonymous,
        content = this.content,
    )
}
