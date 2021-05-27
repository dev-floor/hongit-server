package com.devfloor.untitled.article.application.request

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.user.domain.User

data class ArticleRequest(
    val options: List<String>,
    val title: String?,
    val anonymous: Boolean,
    val content: String,
    val hashtags: List<String>,
) {
    val hasOptions get() = options.isNotEmpty()

    fun toArticle(author: User): Article =
        Article(
            title = this.title,
            anonymous = this.anonymous,
            content = this.content,
            author = author,
        )
}
