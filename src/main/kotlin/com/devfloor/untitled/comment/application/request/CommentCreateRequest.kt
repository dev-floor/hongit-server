package com.devfloor.untitled.comment.application.request

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.comment.domain.Comment
import com.devfloor.untitled.user.domain.User

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
