package com.devfloor.untitled.article.application

import com.devfloor.untitled.article.domain.Article
import com.devfloor.untitled.user.domain.User

data class ArticleRequest(
    val options: List<String>,
    val title: String? = null,
    val anonymous: Boolean,
    val content: String,
    val hashtags: List<String>
) {
    fun toArticle(articleRequest: ArticleRequest, user: User): Article {
        return Article(
            title = articleRequest.title,
            anonymous = articleRequest.anonymous,
            content = articleRequest.content,
            author = user
        )
    }
}
